package onion.api.service;

import onion.api.dto.in.PlotIn;
import onion.api.dto.out.PlotOut;
import onion.domain.plot.Plot;

import java.util.List;


public interface PlotService {

    List<PlotOut> findAllPlots();

    PlotOut createPlot(PlotIn plotIn);
}
