package onion.api.dto.out;

import onion.domain.plot.Plot;
import onion.domain.types.CropType;
import lombok.Data;

@Data
public class PlotOut {

    private final String id;
    private final String name;
    private final Double cultivatedArea;
    private final CropType crop;

    public static PlotOut fromDomain(Plot plot){
        return new PlotOut(
                plot.getId(),
                plot.getName(),
                plot.getCultivatedArea(),
                plot.getCrop()
        );
    }

}
