package onion.api.dto.out;

import lombok.Data;
import onion.domain.plot.TimeSlot;

import java.time.Instant;

@Data
public class TimeSlotOut {

    private final Instant init;
    private final Integer durationMinutes;
    private final Integer amountWater;
    private final String status;

    public static TimeSlotOut fromDomain(TimeSlot slot){
        return new TimeSlotOut(
                slot.getInit(),
                slot.getDurationMinutes(),
                slot.getAmountWater(),
                slot.getStatus().name()
        );
    }

}
