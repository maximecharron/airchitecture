package ca.ulaval.glo4003.air.transfer.flight;


import ca.ulaval.glo4003.air.domain.flight.AirCargoFlight;
import ca.ulaval.glo4003.air.transfer.flight.dto.AirCargoFlightDto;

public class AirCargoFlightAssembler {

    public AirCargoFlightDto create(AirCargoFlight airCargoFlight) {
        AirCargoFlightDto airCargoFlightDto = new AirCargoFlightDto();
        airCargoFlightDto.airlineCompany = airCargoFlight.getAirlineCompany();
        airCargoFlightDto.departureDate = airCargoFlight.getDepartureDate();
        airCargoFlightDto.departureAirport = airCargoFlight.getDepartureAirport();
        airCargoFlightDto.arrivalAirport = airCargoFlight.getArrivalAirport();
        airCargoFlightDto.price = airCargoFlight.getPrice();
        return airCargoFlightDto;
    }
}
