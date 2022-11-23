package onion.api;

import onion.api.dto.in.PlotIn;
import onion.api.dto.out.PlotOut;
import onion.api.service.PlotService;
import onion.domain.plot.Plot;
import onion.domain.plot.PlotRepository;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlotServiceTest {

    public EasyRandom random;

    @MockBean
    private PlotRepository plotRepository;

    @Autowired
    private PlotService plotService;

    @Before
    public void setUp() throws Exception {
         this.random = new EasyRandom();
    }

    @Test
    public void findAllPlots_callService() {

        plotService.findAllPlots();

        verify(plotRepository).findAll();
    }

    @Test
    public void findAllPlots_returnList() {

        Plot plot = random.nextObject(Plot.class).toBuilder().build();
        List<Plot> plots = List.of(plot);
        when(plotRepository.findAll()).thenReturn(plots);

        List<PlotOut> result = plotService.findAllPlots();

        assertThat(result, is(Collections.singletonList(PlotOut.fromDomain(plot))));

    }

    @Test
    public void createPlot_returnValue() {

        PlotIn plotIn = random.nextObject(PlotIn.class);
        Plot plot = plotIn.toDomain();
        when(plotRepository.save(any(Plot.class))).thenReturn(plot);

        PlotOut result = plotService.createPlot(plotIn);

        assertThat(result, is(PlotOut.fromDomain(plot)));

    }


}