package com.example.demo.services;

import com.example.demo.data.entities.ClimateSummary;
import com.example.demo.data.repositories.ClimateRepository;
import com.example.demo.models.ClimateModel;
import com.example.demo.models.PageModel;
import com.example.demo.models.PagingRequestModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ClimateService {

    private final ClimateRepository climateRepository;

    public ClimateService(ClimateRepository climateRepository) {
        this.climateRepository = climateRepository;
    }

    public PageModel<ClimateModel> retrieveClimateData(PagingRequestModel pagingRequestModel) {
        List<ClimateSummary> climateSummaries = climateRepository.findAll(pagingRequestModel);
        PageModel<ClimateModel> pageModel = new PageModel<>(toClimateModels.apply(climateSummaries));
        pageModel.setDraw(pagingRequestModel.getDraw());
        pageModel.setRecordsTotal(climateRepository.count());
        pageModel.setRecordsFiltered(climateRepository.count(pagingRequestModel));
        return pageModel;
    }

    public List<ClimateModel> retrieveClimateDetails(String stationName) {
        return toClimateModels.apply(climateRepository.findByStationName(stationName));
    }

    public static Function<ClimateSummary, ClimateModel> toClimateModel = climateSummary ->
            ClimateModel.Builder.aClimateModel(climateSummary.getStationName(), climateSummary.getProvince(), climateSummary.getReadingDate())
                    .withMaxMonthlyTemperature(climateSummary.getMaxMonthlyTemperature())
                    .withMinMonthlyTemperature(climateSummary.getMinMonthlyTemperature())
                    .withMeanTemperature(climateSummary.getMeanTemperature())
                    .build();

    public static Function<List<ClimateSummary>, List<ClimateModel>> toClimateModels = climateSummaries ->
            climateSummaries.stream().map(toClimateModel).collect(Collectors.toList());

}
