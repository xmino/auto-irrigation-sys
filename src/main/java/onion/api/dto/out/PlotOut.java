package onion.api.dto.out;

import lombok.Data;
import onion.domain.plot.Plot;
import onion.domain.types.CropType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PlotOut {

    private final String id;
    private final String name;
    private final Double cultivatedArea;
    private final CropType crop;
    private final List<TimeSlotOut> timeSlots;

    public static PlotOut fromDomain(Plot plot){
        return new PlotOut(
                plot.getId(),
                plot.getName(),
                plot.getCultivatedArea(),
                plot.getCrop(),
                plot.getTimeSlots()!=null
                        ? plot.getTimeSlots().stream().map(TimeSlotOut::fromDomain).collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }

}
