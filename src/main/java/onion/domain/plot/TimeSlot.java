package onion.domain.plot;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onion.domain.types.SlotStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Data
@Entity(name = "time_slot")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {

    @Id
    @Column
    private String id;

    @Column
    @NotNull
    private Instant init;

    @Column(name = "duration_minutes")
    @NotNull
    private Integer durationMinutes;

    @Column(name = "amount_water")
    @NotNull
    private Integer amountWater;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SlotStatus status;

    @CreationTimestamp
    @Column(name = "created_time")
    @NotNull
    private Instant createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    private Instant updatedTime;

    @ManyToOne
    @JoinColumn(name = "plot_id")
    private Plot plot;

}
