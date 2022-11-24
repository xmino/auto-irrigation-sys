package onion.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onion.api.dto.in.PlotIn;
import onion.api.dto.in.TimeSlotIn;
import onion.api.dto.out.PlotOut;
import onion.api.service.PlotService;
import onion.domain.plot.Plot;
import onion.domain.plot.PlotRepository;
import onion.domain.plot.TimeSlot;
import onion.domain.plot.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlotServiceApi implements PlotService {

    private final PlotRepository plotRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public List<PlotOut> findAllPlots() {
        return plotRepository.findAll().stream().map(PlotOut::fromDomain).collect(Collectors.toList());
    }

    @Override
    public PlotOut savePlot(Plot plot) {
        return PlotOut.fromDomain(plotRepository.save(plot));
    }
    @Override
    public PlotOut updatePlot(Plot plot) {
        Plot currentPlot = plotRepository.getReferenceById(plot.getId());
        Plot updatedPlot = plot.toBuilder().createdTime(currentPlot.getCreatedTime()).build();
        return PlotOut.fromDomain(plotRepository.save(updatedPlot));
    }

    @Override
    public void saveTimeSlot(TimeSlotIn slot, String plotId) {
        Plot plot = plotRepository.getReferenceById(plotId);
        TimeSlot timeSlot = slot.toDomain().toBuilder().plot(plot).build();
        timeSlotRepository.save(timeSlot);
    }

}
