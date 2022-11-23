package onion.infra.controller;

import onion.api.service.PlotService;
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

    @MockBean
    private PlotService plotService;

    @Autowired
    private RoutePlotController plotController;

    @Test
    public void listAll_callService() {

        plotController.listAll();

        verify(plotService).findAllPlots();

    }
}