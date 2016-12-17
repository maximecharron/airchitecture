package ca.ulaval.glo4003.air.transfer.flight;

import ca.ulaval.glo4003.air.domain.flight.SeatsPricing;
import ca.ulaval.glo4003.air.transfer.flight.dto.SeatsPricingDto;

public class SeatsPricingAssembler {

    public SeatsPricingDto create(SeatsPricing seatsPricing) {
        SeatsPricingDto seatsPricingDto = new SeatsPricingDto();
        seatsPricingDto.economicSeatsPrice = seatsPricing.getEconomicSeatsPrice();
        seatsPricingDto.regularSeatsPrice = seatsPricing.getRegularSeatsPrice();
        seatsPricingDto.businessSeatsPrice = seatsPricing.getBusinessSeatsPrice();

        return seatsPricingDto;
    }
}
