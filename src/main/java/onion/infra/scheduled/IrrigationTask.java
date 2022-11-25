package onion.infra.scheduled;

import onion.domain.plot.TimeSlot;
import onion.infra.rest.RestIrrigationSystem;


public class IrrigationTask implements Runnable{

    private TimeSlot timeSlot;

    private RestIrrigationSystem restIrrigationSystem;

    public IrrigationTask(TimeSlot timeSlot, RestIrrigationSystem restIrrigationSystem){
        this.timeSlot = timeSlot;
        this.restIrrigationSystem = restIrrigationSystem;
    }

    @Override
    public void run(){
        //Here call the interface with sensor in this case I assume is a rest service available
        try {
            restIrrigationSystem.irrigatePlot(timeSlot);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
