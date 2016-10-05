package ca.ulaval.glo4003.ws.domain.weightDetection;

import ca.ulaval.glo4003.ws.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.ws.api.weightDetection.dto.WeightDetectionDto;
import ca.ulaval.glo4003.ws.domain.flight.Flight;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WeightDetectionAssembler {

    public WeightDetectionDto create(double weight) {
        WeightDetectionDto weightDetectionDto = new WeightDetectionDto();
        weightDetectionDto.weight = weight;
        return weightDetectionDto;
    }
}
