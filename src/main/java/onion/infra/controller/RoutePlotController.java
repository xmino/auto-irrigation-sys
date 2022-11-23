package onion.infra.controller;

import lombok.RequiredArgsConstructor;
import onion.api.dto.in.PlotIn;
import onion.api.dto.out.PlotOut;
import onion.api.service.PlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "auto-irrigation/v1/plots", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
public class RoutePlotController {


    private final PlotService plotService;

    @GetMapping()
    public ResponseEntity<List<PlotOut>> listAll() {
        return new ResponseEntity<>(plotService.findAllPlots(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PlotOut> createPlot(@Valid @RequestBody PlotIn plotIn){
        return new ResponseEntity<>(plotService.createPlot(plotIn), HttpStatus.CREATED);
    }
}
