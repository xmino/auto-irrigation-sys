package onion.infra.rest;

import com.google.common.annotations.VisibleForTesting;
import onion.api.service.PlotService;
import onion.domain.plot.TimeSlot;
import onion.domain.types.SlotStatus;
import onion.infra.alerts.SensorAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Logger;

@Component
public class RestIrrigationSystem {

    @Value("${plot.sensor-retry}")
    private int sensorRetry;
    @Value("${plot.sensor-wait-time}")
    private int sensorWaitTime;

    private final SensorAlert alert;

    private PlotService plotService;

    @Autowired
    public RestIrrigationSystem(@Lazy PlotService plotService, SensorAlert alert) {
        this.plotService = plotService;
        this.alert = alert;
    }

    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public void irrigatePlot(TimeSlot slot) throws InterruptedException {
        long delay = 0;
        boolean retry = true;
        int attempt = 1;
        do{
            try{
                Thread.sleep(delay);
                HttpStatus response = mockRestService(slot);
                if(attempt < sensorRetry && response.equals(HttpStatus.REQUEST_TIMEOUT) || response.equals(HttpStatus.GATEWAY_TIMEOUT)){
                    delay = sensorWaitTime;
                    attempt++;
                }else if (response.equals(HttpStatus.OK)){
                    retry = false;
                    Logger.getAnonymousLogger().info("Sensor Irrigating...");
                    TimeSlot newStatus = slot.toBuilder().status(SlotStatus.IRRIGATING).build();
                    plotService.updateTimeSlot(newStatus);
                }else{
                    retry = false;
                    alert.create(slot, "Sensor not response");
                    TimeSlot newStatus = slot.toBuilder().status(SlotStatus.ERROR).build();
                    plotService.updateTimeSlot(newStatus);
                }
            }catch (RestClientResponseException e){
                alert.create(slot, "Sensor error: " + e.getMessage());
                TimeSlot newStatus = slot.toBuilder().status(SlotStatus.ERROR).build();
                plotService.updateTimeSlot(newStatus);
                Thread.currentThread().interrupt();
            }
        }while(retry);
    }

    @VisibleForTesting
    HttpStatus mockRestService(TimeSlot slot) throws RestClientResponseException {
        Random rand;
        try {
            rand = SecureRandom.getInstanceStrong();
            Logger.getAnonymousLogger().info("Sensor try irrigate Plot: " + slot.getPlot().getId());
            return rand.nextBoolean() ? HttpStatus.OK : HttpStatus.REQUEST_TIMEOUT;
        } catch (Exception e) {
            throw new RestClientResponseException("rest error",1,"",null,null,null);
        }

    }

}
