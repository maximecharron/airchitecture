package ca.ulaval.glo4003.air.domain.user;

public class UserPreferences {
    private boolean showWeightFilteredAlert;

    public boolean userWantsToSeeWeightFilteredAlert() {
        return showWeightFilteredAlert;
    }

    public boolean isShowWeightFilteredAlert() {
        return showWeightFilteredAlert;
    }

    public void setShowWeightFilteredAlert(boolean showWeightFilteredAlert) {
        this.showWeightFilteredAlert = showWeightFilteredAlert;
    }
}
