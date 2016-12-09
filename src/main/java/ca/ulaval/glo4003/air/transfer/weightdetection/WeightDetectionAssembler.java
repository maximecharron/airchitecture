package ca.ulaval.glo4003.air.transfer.weightdetection;

import ca.ulaval.glo4003.air.transfer.weightdetection.dto.WeightDetectionDto;

public class WeightDetectionAssembler {

    public WeightDetectionDto create(double weight) {
        WeightDetectionDto weightDetectionDto = new WeightDetectionDto();
        weightDetectionDto.weight = weight;
        return weightDetectionDto;
    }
}
