package onion.infra.controller;

import onion.api.dto.in.PlotIn;
import onion.api.service.PlotService;
import onion.domain.plot.Plot;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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
        this.random = new EasyRandom();
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

        verify(plotService).createPlot(plotIn);

    }
}