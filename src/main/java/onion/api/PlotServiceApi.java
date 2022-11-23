package onion.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onion.api.dto.in.PlotIn;
import onion.api.dto.out.PlotOut;
import onion.api.service.PlotService;
import onion.domain.plot.Plot;
import onion.domain.plot.PlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlotServiceApi implements PlotService {

    private final PlotRepository plotRepository;

    @Override
    public List<PlotOut> findAllPlots() {
        return plotRepository.findAll().stream().map(PlotOut::fromDomain).collect(Collectors.toList());
    }

    @Override
    public PlotOut createPlot(PlotIn plotIn) {
        return PlotOut.fromDomain(plotRepository.save(plotIn.toDomain()));
    }
}
