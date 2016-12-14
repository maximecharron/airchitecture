package ca.ulaval.glo4003.air.transfer.user.dto;

import java.util.Map;

public class UserDto {

    public String emailAddress;
    public String token;
    public boolean hidesWeightFilteredAlert;
    public boolean isAdmin;
    public Map<String, Integer> preferredDestinations;
}
