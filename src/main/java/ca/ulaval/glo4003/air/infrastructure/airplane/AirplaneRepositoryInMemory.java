package ca.ulaval.glo4003.air.infrastructure.airplane;

import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.airplane.AirplaneRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AirplaneRepositoryInMemory implements AirplaneRepository {

    private Map<String, Airplane> airplanes = new HashMap<>();

    @Override
    public void save(Airplane airplane) {
        airplanes.put(airplane.getSerialNumber(), airplane);
    }

    @Override
    public List<Airplane> findAll() {
        return airplanes.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Airplane> findAllAirLourd() {
        return airplanes.values().stream().filter(Airplane::isAirLourd).collect(Collectors.toList());
    }

    @Override
    public Optional<Airplane> find(String serialNumber) {
        return airplanes.values().stream().filter(airplane -> airplane.hasSerialNumber(serialNumber)).findAny();
    }
}
