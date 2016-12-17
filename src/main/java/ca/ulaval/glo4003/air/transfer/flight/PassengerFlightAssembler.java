package ca.ulaval.glo4003.air.transfer.flight;

import ca.ulaval.glo4003.air.domain.flight.AirCargoFlight;
import ca.ulaval.glo4003.air.domain.flight.FlightSearchResult;
import ca.ulaval.glo4003.air.domain.flight.PassengerFlight;
import ca.ulaval.glo4003.air.transfer.flight.dto.FlightSearchResultDto;
import ca.ulaval.glo4003.air.transfer.flight.dto.PassengerFlightDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PassengerFlightAssembler {

    private final AvailableSeatsAssembler availableSeatsAssembler;
    private final SeatsPricingAssembler seatsPricingAssembler;
    private final AirCargoFlightAssembler airCargoFlightAssembler;

    public PassengerFlightAssembler(AvailableSeatsAssembler availableSeatsAssembler, SeatsPricingAssembler seatsPricingAssembler, AirCargoFlightAssembler airCargoFlightAssembler) {
        this.availableSeatsAssembler = availableSeatsAssembler;
        this.seatsPricingAssembler = seatsPricingAssembler;
        this.airCargoFlightAssembler = airCargoFlightAssembler;
    }

    public PassengerFlightDto create(PassengerFlight flight, double weight) {
        PassengerFlightDto passengerFlightDto = new PassengerFlightDto();
        passengerFlightDto.airlineCompany = flight.getAirlineCompany();
        passengerFlightDto.departureDate = flight.getDepartureDate();
        passengerFlightDto.departureAirport = flight.getDepartureAirport();
        passengerFlightDto.arrivalAirport = flight.getArrivalAirport();
        passengerFlightDto.availableSeatsDto = availableSeatsAssembler.create(flight.getAvailableSeats());
        passengerFlightDto.seatsPricingDto = seatsPricingAssembler.create(flight.getSeatsPricing());
        passengerFlightDto.hasAdditionalWeightOption = flight.hasAdditionalWeightOption();
        passengerFlightDto.acceptsAdditionalWeight = flight.acceptsAdditionalWeight(weight);
        return passengerFlightDto;
    }

    public FlightSearchResultDto create(FlightSearchResult flightSearchResult) {
        FlightSearchResultDto flightSearchResultDto = new FlightSearchResultDto();
        flightSearchResultDto.flights = this.create(flightSearchResult.getFlightsFilteredByWeight(), flightSearchResult.getWeight(), flightSearchResult.getFlightsWithAirCargo());
        flightSearchResultDto.flightsWereFilteredByWeight = flightSearchResult.isFlightsWereFilteredByWeight();
        return flightSearchResultDto;
    }

    private List<PassengerFlightDto> create(List<PassengerFlight> flights, double weight, Map<PassengerFlight, AirCargoFlight> flightsWithAirCargo) {
        return flights.stream().map(flight -> {
            if (flightsWithAirCargo.containsKey(flight)) return create(flight, weight, flightsWithAirCargo.get(flight));
            else return create(flight, weight);
        }).collect(Collectors.toList());
    }

    public PassengerFlightDto create(PassengerFlight flight, double weight, AirCargoFlight airCargoFlight) {
        PassengerFlightDto passengerFlightDto = create(flight, weight);
        passengerFlightDto.airCargoFlight = airCargoFlightAssembler.create(airCargoFlight);
        return passengerFlightDto;
    }
}
