package onion.infra.rest;

import onion.api.service.PlotService;
import onion.domain.plot.TimeSlot;
import onion.domain.types.SlotStatus;
import onion.infra.alerts.SensorAlert;
import onion.infra.event.SqsEventModule;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestIrrigationSystemTest {

    public EasyRandom random;
    @MockBean
    private SensorAlert alert;

    @MockBean
    private PlotService plotService;

    @Autowired
    private RestIrrigationSystem irrigationSystem;
    private RestIrrigationSystem spyIrrigationSystem;


    @Before
    public void setUp() throws Exception {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(Integer.class, () -> new Random().nextInt(999999));
        this.random = new EasyRandom(parameters);
        spyIrrigationSystem = spy(irrigationSystem);
    }

    @Test
    public void irrigatePlot_callCreateAlert() throws InterruptedException {
        TimeSlot slot = random.nextObject(TimeSlot.class);

        when(spyIrrigationSystem.mockRestService(slot)).thenReturn(HttpStatus.BAD_REQUEST);
        spyIrrigationSystem.irrigatePlot(slot);

        verify(alert).create(eq(slot), any(String.class));
    }

    @Test
    public void irrigatePlot_whenOkRequest_notCreateAlert() throws InterruptedException {
        TimeSlot slot = random.nextObject(TimeSlot.class);

        when(spyIrrigationSystem.mockRestService(slot)).thenReturn(HttpStatus.OK);
        spyIrrigationSystem.irrigatePlot(slot);

        verify(alert, never()).create(eq(slot), any(String.class));
    }

    @Test
    public void irrigatePlot_whenRequestTimeOut_retryAndCreateAlert() throws InterruptedException {
        TimeSlot slot = random.nextObject(TimeSlot.class);

        when(spyIrrigationSystem.mockRestService(slot)).thenReturn(HttpStatus.REQUEST_TIMEOUT);
        spyIrrigationSystem.irrigatePlot(slot);

        verify(spyIrrigationSystem, times(3)).mockRestService(slot);
        verify(alert).create(eq(slot), any(String.class));
    }

    @Test
    public void irrigatePlot_whenOkRequest_updateStatusIrrigating() throws InterruptedException {
        TimeSlot slot = random.nextObject(TimeSlot.class);

        when(spyIrrigationSystem.mockRestService(slot)).thenReturn(HttpStatus.OK);
        spyIrrigationSystem.irrigatePlot(slot);

        verify(plotService).updateTimeSlot(slot.toBuilder().status(SlotStatus.IRRIGATING).build());
    }

    @Test
    public void irrigatePlot_whenNotOkRequest_updateStatusError() throws InterruptedException {
        TimeSlot slot = random.nextObject(TimeSlot.class);

        when(spyIrrigationSystem.mockRestService(slot)).thenReturn(HttpStatus.FORBIDDEN);
        spyIrrigationSystem.irrigatePlot(slot);

        verify(plotService).updateTimeSlot(slot.toBuilder().status(SlotStatus.ERROR).build());
    }


}