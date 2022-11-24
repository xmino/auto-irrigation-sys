package onion.api.dto.in;

import lombok.Data;
import onion.domain.plot.Plot;
import onion.domain.types.CropType;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;

@Data
public class PlotIn {

    @NotEmpty(message = "name is required.")
    @Size(min = 2, max = 60, message = "The length of name must be between 2 and 60 characters.")
    private final String name;
    @NotNull(message = "cultivatedArea is required.")
    @DecimalMin("0.0")
    private final Double cultivatedArea;
    @NotNull(message = "crop is required.")
    private final String crop;

    public Plot toDomain(){
        return toDomain(UUID.randomUUID().toString());
    }

    public Plot toDomain(String id){
        return Plot.builder()
                   .id(id)
                   .name(getName())
                   .crop(CropType.safeLookup(getCrop()))
                   .cultivatedArea(getCultivatedArea())
                   .createdTime(Instant.now())
                   .updatedTime(Instant.now())
                   .build();
    }

}
