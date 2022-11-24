package onion.infra.controller;

import lombok.RequiredArgsConstructor;
import onion.api.dto.in.PlotIn;
import onion.api.dto.in.TimeSlotIn;
import onion.api.dto.out.PlotOut;
import onion.api.service.PlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
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
        return new ResponseEntity<>(plotService.savePlot(plotIn.toDomain()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editPlot(@Valid @RequestBody PlotIn plotIn, @PathVariable String id){
        try{
            return new ResponseEntity<>(plotService.updatePlot(plotIn.toDomain(id)), HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/configure/{plotId}")
    public ResponseEntity<String> configurePlot(@Valid @RequestBody TimeSlotIn slot, @PathVariable String plotId){
        try{
            plotService.saveTimeSlot(slot, plotId);
            return new ResponseEntity<>("Time Slot added",HttpStatus.OK);
        }catch (JpaObjectRetrievalFailureException e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
