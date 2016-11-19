package ca.ulaval.glo4003.air.domain.airplane;

import java.util.List;
import java.util.Optional;

public interface AirplaneRepository {

    void save(Airplane airplane);

    List<Airplane> findAll();
    List<Airplane> findAllAirLourd();

    Optional<Airplane> find(String serialNumber);
}
