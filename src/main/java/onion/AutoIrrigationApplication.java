package onion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutoIrrigationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoIrrigationApplication.class, args);
    }

}
