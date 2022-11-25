package onion.infra.alert;

import onion.domain.plot.TimeSlot;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SensorAlertTest {

    public EasyRandom random;
    @MockBean
    private SqsEventModule publish;

    @Autowired
    private SensorAlert sensorAlert;


    @Before
    public void setUp() throws Exception {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(Integer.class, () -> new Random().nextInt(999999));
        this.random = new EasyRandom(parameters);
    }

    @Test
    public void create_callSendMessage() {
        TimeSlot slot = random.nextObject(TimeSlot.class);
        String message = UUID.randomUUID().toString();
        sensorAlert.create(slot, message);

        verify(publish).sendMessage(message);

    }

}