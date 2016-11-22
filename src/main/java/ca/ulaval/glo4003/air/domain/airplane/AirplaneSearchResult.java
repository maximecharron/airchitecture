package ca.ulaval.glo4003.air.domain.airplane;

import java.util.List;

public class AirplaneSearchResult {

    private final List<Airplane> airplanes;

    public AirplaneSearchResult(List<Airplane> airplanes) {
        this.airplanes = airplanes;
    }

    public List<Airplane> getAirplanes() {
        return airplanes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AirplaneSearchResult that = (AirplaneSearchResult) o;
        return airplanes != null ? airplanes.equals(that.airplanes) : that.airplanes == null;

    }

    @Override
    public int hashCode() {
        return getAirplanes().hashCode();
    }
}
