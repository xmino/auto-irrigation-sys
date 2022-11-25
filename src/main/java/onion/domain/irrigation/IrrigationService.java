package onion.domain.irrigation;

import onion.domain.plot.TimeSlot;

public interface IrrigationService {

    void scheduleIrrigation(TimeSlot slotPlot);

}
