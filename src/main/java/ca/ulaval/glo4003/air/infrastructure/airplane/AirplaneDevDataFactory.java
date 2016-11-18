package ca.ulaval.glo4003.air.infrastructure.airplane;

import ca.ulaval.glo4003.air.domain.airplane.AirLegerAirplane;
import ca.ulaval.glo4003.air.domain.airplane.AirLourdAirplane;
import ca.ulaval.glo4003.air.domain.airplane.AirMoyenAirplane;
import ca.ulaval.glo4003.air.domain.airplane.Airplane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AirplaneDevDataFactory {

    public List<Airplane> createMockData() {
        Airplane airplane1 = new AirLegerAirplane(100, "a-darius");
        Airplane airplane2 = new AirMoyenAirplane(2, "a-rucker");
        Airplane airplane3 = new AirLegerAirplane(42, "a-wagon");
        Airplane airplane4 = new AirLourdAirplane(17, 1000, "a-wheel");
        Airplane airplane5 = new AirLourdAirplane(13, 0, "a-rock");

        return new ArrayList<>(Arrays.asList(airplane1, airplane2, airplane3, airplane4, airplane5));
    }
}
