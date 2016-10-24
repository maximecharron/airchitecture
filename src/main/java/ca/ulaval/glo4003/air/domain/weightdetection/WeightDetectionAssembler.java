package ca.ulaval.glo4003.air.domain.weightdetection;

import ca.ulaval.glo4003.air.api.weightdetection.dto.WeightDetectionDto;

public class WeightDetectionAssembler {

    public WeightDetectionDto create(double weight) {
        WeightDetectionDto weightDetectionDto = new WeightDetectionDto();
        weightDetectionDto.weight = weight;
        return weightDetectionDto;
    }
}