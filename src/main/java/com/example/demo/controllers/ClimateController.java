package com.example.demo.controllers;

import com.example.demo.models.ClimateModel;
import com.example.demo.models.PageModel;
import com.example.demo.representations.ClimateDetailRepresentation;
import com.example.demo.representations.PageRepresentation;
import com.example.demo.representations.PagingRequestRepresentation;
import com.example.demo.representations.views.DataViews;
import com.example.demo.services.ClimateService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ClimateController {

    private final ClimateService climateService;

    public ClimateController(ClimateService climateService) {
        this.climateService = climateService;
    }

    @GetMapping({"/", "/index"})
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView("summary");
        return modelAndView;
    }

    @GetMapping(value = "/detail")
    public ModelAndView getClimateDataDetailsWithRequestParams(@RequestParam String stationName) {
        List<ClimateDetailRepresentation> climateDetails = ClimateDetailRepresentation.toClimateDetails.apply(climateService.retrieveClimateDetails(stationName));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("climateDetails", climateDetails);
        return modelAndView;
    }

    @JsonView(DataViews.ClimateSummaryView.class)
    @PostMapping(value = "/summary", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageRepresentation<ClimateDetailRepresentation>> getAllClimateData(@RequestBody PagingRequestRepresentation pagingRequestRepresentation) {
        System.out.println("called");
        System.out.println(pagingRequestRepresentation.getSearch().getDateRange());
        PageModel<ClimateModel> model = climateService.retrieveClimateData(PagingRequestRepresentation.toPagingModel.apply(pagingRequestRepresentation));
        System.out.println(model);
        PageRepresentation<ClimateDetailRepresentation> representationPageRepresentation = PageRepresentation.toPageRepresentation.apply(model);
        System.out.println(representationPageRepresentation);

        return ResponseEntity.ok(representationPageRepresentation);
    }

}
