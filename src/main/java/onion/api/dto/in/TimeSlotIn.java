package onion.api.dto.in;

import lombok.Data;
import onion.domain.plot.TimeSlot;
import onion.domain.types.SlotStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Data
public class TimeSlotIn {

    @NotNull(message = "init Time is required.")
    private final Instant init;

    @NotNull(message = "durationMinutes is required.")
    @Min(0)
    private final Integer durationMinutes;

    @NotNull(message = "amountWater is required.")
    @Min(0)
    private final Integer amountWater;

    @NotEmpty(message = "status is required.")
    private final String status;


    public TimeSlot toDomain(){
        return TimeSlot.builder()
                .id(UUID.randomUUID().toString())
                .init(getInit())
                .durationMinutes(getDurationMinutes())
                .amountWater(getAmountWater())
                .status(SlotStatus.safeLookup(getStatus()))
                .createdTime(Instant.now())
                .build();
    }

}
