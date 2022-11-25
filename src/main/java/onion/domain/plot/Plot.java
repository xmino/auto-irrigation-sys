package onion.domain.plot;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onion.domain.types.CropType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "plots")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Plot {

    @Id
    @Column
    private String id;

    @Column
    @NotNull
    private String name;

    @Column(name = "cultivated_area")
    @NotNull
    private Double cultivatedArea;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CropType crop;

    @CreationTimestamp
    @Column(name = "created_time")
    @NotNull
    private Instant createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    @NotNull
    private Instant updatedTime;

    @OneToMany(mappedBy = "plot")
    private List<TimeSlot> timeSlots = new ArrayList<>();

}
