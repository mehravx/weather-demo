package com.weather.demo.data.repositories;

import com.weather.demo.config.ClimateFileConfig;
import com.weather.demo.data.entities.ClimateSummary;
import com.weather.demo.data.entities.ClimateSummaryComparators;
import com.weather.demo.data.entities.FilterCriteria;
import com.weather.demo.exceptions.DataLoadingException;
import com.weather.demo.models.Direction;
import com.weather.demo.models.OrderModel;
import com.weather.demo.models.PagingRequestModel;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Component
public class CSVDataRepository implements ClimateRepository<ClimateSummary> {

    private final static Logger logger = LoggerFactory.getLogger(CSVDataRepository.class);
    private final List<ClimateSummary> cachedDataStore = new ArrayList<>();

    private ClimateFileConfig climateFileConfig;

    public CSVDataRepository(ClimateFileConfig climateFileConfig) {
        this.climateFileConfig = climateFileConfig;
    }

    @PostConstruct
    public void loadData() {
        cachedDataStore.addAll(
                loadData(
                        ClimateSummary.class,
                        climateFileConfig.getFilePath(),
                        climateFileConfig.getSeparator()
                )
        );
        if (cachedDataStore.isEmpty()) throw new DataLoadingException("No data found to load");
    }

    public <T> List<T> loadData(Class<T> type, String fileName, char delimiter) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(delimiter);
            CsvMapper mapper = new CsvMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(CsvParser.Feature.TRIM_SPACES);
            mapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
            InputStream file = new ClassPathResource(fileName, this.getClass().getClassLoader()).getInputStream();
            MappingIterator<T> it = mapper.readerFor(type).with(bootstrapSchema).readValues(file);
            // can use it.readAll if the data-set being read is small in size
            List<T> result = new ArrayList<>();
            it.forEachRemaining(result::add);
            return result;
        } catch (IOException exception) {
            throw new DataLoadingException("Error occurred while loading object list from file " + fileName, exception);
        }
    }

    @Override
    public List<ClimateSummary> findByStationName(String stationName) {
        return findAll().stream()
                .filter(climateSummary -> climateSummary.getStationName().isEmpty() ? false : climateSummary.getStationName().equalsIgnoreCase(stationName))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClimateSummary> findAll(PagingRequestModel pagingRequestModel) {

        List<ClimateSummary> climateSummaries = findAll();
        if (pagingRequestModel == null) return climateSummaries;

        if (pagingRequestModel.getLength() < 0) pagingRequestModel.setLength(climateSummaries.size());

        Comparator<ClimateSummary> climateSummaryComparator = getComparator(pagingRequestModel);
        BiPredicate dateRangeFilter = getFilter(pagingRequestModel);

        return climateSummaries.stream()
                .sorted(climateSummaryComparator)
                .filter(climateSummary -> dateRangeFilter.test(climateSummary, pagingRequestModel.getSearch()))
                .filter(climateSummary -> FilterCriteria.exists.test(climateSummary, pagingRequestModel.getSearch()))
                .skip(pagingRequestModel.getStart())
                .limit(pagingRequestModel.getLength())
                .collect(Collectors.toList());
    }

    @Override
    public List<ClimateSummary> findAll() {
        return Collections.unmodifiableList(cachedDataStore);
    }

    @Override
    public int count(PagingRequestModel pagingRequestModel) {
        return (int) findAll().stream()
                .sorted(getComparator(pagingRequestModel))
                .filter(climateSummary -> getFilter(pagingRequestModel).test(climateSummary, pagingRequestModel.getSearch()))
                .filter(climateSummary -> FilterCriteria.exists.test(climateSummary, pagingRequestModel.getSearch()))
                .count();
    }

    @Override
    public int count() {
        return findAll().isEmpty() ? 0 : findAll().size();
    }

    private BiPredicate getFilter(PagingRequestModel pagingRequestModel) {
        if (pagingRequestModel == null || pagingRequestModel.getSearch() == null)
            return FilterCriteria.ALWAYS_TRUE_PREDICATE;
        return FilterCriteria.getDateFilters(pagingRequestModel.getSearch());
    }

    private Comparator<ClimateSummary> getComparator(PagingRequestModel pagingRequestModel) {
        try {
            List<OrderModel> orders = pagingRequestModel.getOrder();
            int columnIndex = orders.get(0).getColumn();
            Direction direction = orders.get(0).getDir();
            String columnName = pagingRequestModel.getColumns().get(columnIndex).getData();
            return ClimateSummaryComparators.getComparator(columnName, direction.name());
        } catch (Exception exception) {
            /**
             * well, if you are here, then 99.99999 % of the times it'll be for a NPE, return the empty comparator..
             * guess thats better in this case rather than failing the request
             * although, this means that the client didn't fully understand the usage of the pagingRequestModel
             *
             * alternatively, perform validation checks against the pagingRequestRepresentation in the Controller/Representation layer
             * for all possibles combinations/scenarios & throw specific exceptions
             *
             */
            logger.warn("Returning an empty comparator", exception);
            return ClimateSummaryComparators.EMPTY_COMPARATOR;
        }

    }

}