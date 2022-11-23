package onion.infra.controller;

import onion.api.service.PlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auto-irrigation/v1/plots", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
public class RoutePlotController {


    private final PlotService plotService;

    @GetMapping()
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(plotService.findAllPlots(), HttpStatus.OK);
    }
}
