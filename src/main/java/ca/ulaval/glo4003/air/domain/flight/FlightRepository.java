package ca.ulaval.glo4003.air.domain.flight;

public interface FlightRepository {
    void save(Flight flight);
    FlightQueryBuilder query();
}
