package onion.infra.alerts;

import onion.domain.plot.TimeSlot;
import onion.infra.event.SqsEventModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SensorAlert {

    @Autowired
    SqsEventModule publish;

    public void create(TimeSlot slot, String message) {
        Logger.getAnonymousLogger().warning(String.format("Generating Alert for plot: %s cause: %s", slot.getPlot().getId(),message));
        publish.sendMessage(message);
    }

}
