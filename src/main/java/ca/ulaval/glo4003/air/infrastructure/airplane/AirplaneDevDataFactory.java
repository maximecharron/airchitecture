package ca.ulaval.glo4003.air.infrastructure.airplane;

import ca.ulaval.glo4003.air.domain.airplane.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AirplaneDevDataFactory {

    public List<Airplane> createMockData() {
        Airplane airplane1 = new AirLegerAirplane(100, true, "a-darius");

        SeatMap seatMap2 = new SeatMap(1, 1, 0);
        Airplane airplane2 = new AirMoyenAirplane(seatMap2, true, "a-rucker");

        Airplane airplane3 = new AirLegerAirplane(42, false, "a-wagon");

        SeatMap seatMap3 = new SeatMap(10, 2, 5);
        Airplane airplane4 = new AirLourdAirplane(seatMap3, 1000, true, "a-wheel");

        SeatMap seatMap4 = new SeatMap(0, 9, 4);
        Airplane airplane5 = new AirLourdAirplane(seatMap4, 0, false, "a-rock");

        return new ArrayList<>(Arrays.asList(airplane1, airplane2, airplane3, airplane4, airplane5));
    }
}
