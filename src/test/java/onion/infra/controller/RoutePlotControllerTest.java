package onion.infra.controller;

import onion.api.dto.in.PlotIn;
import onion.api.dto.in.TimeSlotIn;
import onion.api.service.PlotService;
import onion.domain.plot.Plot;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoutePlotControllerTest {

    public EasyRandom random;
    @MockBean
    private PlotService plotService;

    @Autowired
    private RoutePlotController plotController;

    @Before
    public void setUp() throws Exception {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(Integer.class, () -> new Random().nextInt(999999));
        this.random = new EasyRandom(parameters);
    }

    @Test
    public void listAll_callService() {

        plotController.listAll();

        verify(plotService).findAllPlots();

    }

    @Test
    public void createPlot_callService() {

        PlotIn plotIn = random.nextObject(PlotIn.class);
        plotController.createPlot(plotIn);

        verify(plotService).savePlot(any(Plot.class));

    }

    @Test
    public void editPlot_callService() {

        PlotIn plotIn = random.nextObject(PlotIn.class);
        String id = UUID.randomUUID().toString();
        plotController.editPlot(plotIn, id);

        verify(plotService).updatePlot(any(Plot.class));

    }

    @Test
    public void configurePlot_callService() {

        TimeSlotIn slot = random.nextObject(TimeSlotIn.class);
        String plotId = UUID.randomUUID().toString();
        plotController.configurePlot(slot, plotId);

        verify(plotService).saveTimeSlot(slot,plotId);

    }
}