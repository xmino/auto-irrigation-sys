package onion.infra.scheduled;

import onion.domain.irrigation.IrrigationService;
import onion.domain.plot.TimeSlot;
import onion.infra.rest.RestIrrigationSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class IrrigationScheduler implements IrrigationService {

    @Autowired
    ThreadPoolTaskScheduler scheduler;

    @Autowired
    RestIrrigationSystem irrigation;

    @Override
    public void scheduleIrrigation(TimeSlot slotPlot){
        scheduler.schedule(new IrrigationTask(slotPlot, irrigation), slotPlot.getInit());
    }

}
