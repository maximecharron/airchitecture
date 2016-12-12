package ca.ulaval.glo4003.air.domain.user;

public class UserSettings {

    private boolean hideWeightFilteredAlert;

    public boolean userWantsToHideWeightFilteredAlert() {
        return hideWeightFilteredAlert;
    }

    public void setHideWeightFilteredAlert(boolean hideWeightFilteredAlert) {
        this.hideWeightFilteredAlert = hideWeightFilteredAlert;
    }
}
