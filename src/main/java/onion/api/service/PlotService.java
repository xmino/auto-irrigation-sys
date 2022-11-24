package onion.api.service;

import onion.api.dto.in.PlotIn;
import onion.api.dto.in.TimeSlotIn;
import onion.api.dto.out.PlotOut;
import onion.domain.plot.Plot;
import onion.domain.plot.TimeSlot;

import java.util.List;


public interface PlotService {

    List<PlotOut> findAllPlots();

    PlotOut savePlot(Plot plot);

    PlotOut updatePlot(Plot plot);

    void saveTimeSlot(TimeSlotIn slot, String plotId);
}
