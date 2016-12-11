package ca.ulaval.glo4003.air.domain.user;

import ca.ulaval.glo4003.air.transfer.user.UserAssembler;
import ca.ulaval.glo4003.air.transfer.user.dto.UserDto;
import ca.ulaval.glo4003.air.transfer.user.dto.UserSearchPreferencesDto;
import ca.ulaval.glo4003.air.transfer.user.dto.UserSettingsDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserAssemblerTest {

    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "DEF";
    private static final String A_TOKEN = "aToken";
    private static final boolean A_HIDE_WEIGHT_FILTER_VALUE = false;
    private static final boolean A_VALUE_FOR_AIRVIVANT_SEARCH = true;
    private static final boolean A_VALUE_FOR_ECONOMIC_SEARCH = false;
    private static final boolean A_VALUE_FOR_REGULAR_SEARCH = false;
    private static final boolean A_VALUE_FOR_BUSINESS_SEARCH = false;

    @Mock
    private User user;

    @Mock
    private UserSearchPreferences userSearchPreferences;

    private UserSettingsDto userSettingsDto;

    private UserAssembler userAssembler;

    @Before
    public void setup() {
        userAssembler = new UserAssembler();
        userSettingsDto = new UserSettingsDto();
    }

    @Test
    public void givenAUser_whenCreatingAUserDto_thenItHasAllTheRelevantProperties() {
        givenAUser();

        UserDto userDto = userAssembler.create(user);

        assertHasAllTheRelevantProperties(userDto);
    }

    @Test
    public void givenSearchPreferences_whenCreatingSearchPreferencesDto_thenItHasAllTheRelevantProperties() {
        givenSearchProperties();

        UserSearchPreferencesDto userSearchPreferencesDto = userAssembler.createUserSearchPreferences(userSearchPreferences);

        assertHasAllTheRelevantProperties(userSearchPreferencesDto);
    }

    @Test
    public void givenAUserSettingsDto_whenCreatingUserSettings_thenItHasAllTheRelevantProperties() {
        givenAUserSettingsDto();

        UserSettings userSettings = userAssembler.createUserSettings(userSettingsDto);

        assertHasAllTheRelevantProperties(userSettings);
    }

    private void givenAUser() {
        given(user.getEmailAddress()).willReturn(EMAIL);
        given(user.getPassword()).willReturn(PASSWORD);
        given(user.getToken()).willReturn(A_TOKEN);
    }

    private void givenSearchProperties() {
        given(userSearchPreferences.hasMostlySearchedForAirVivantFlights()).willReturn(A_VALUE_FOR_AIRVIVANT_SEARCH);
        given(userSearchPreferences.hasMostlySearchedForBusinessClassFlights()).willReturn(A_VALUE_FOR_BUSINESS_SEARCH);
        given(userSearchPreferences.hasMostlySearchedForEconomyClassFlights()).willReturn(A_VALUE_FOR_ECONOMIC_SEARCH);
        given(userSearchPreferences.hasMostlySearchedForRegularClassFlights()).willReturn(A_VALUE_FOR_REGULAR_SEARCH);
    }

    private void givenAUserSettingsDto() {
        userSettingsDto.hideWeightFilteredAlert = A_HIDE_WEIGHT_FILTER_VALUE;
    }

    private void assertHasAllTheRelevantProperties(UserDto userDto) {
        assertEquals(EMAIL, userDto.emailAddress);
        assertEquals(A_TOKEN, userDto.token);
    }

    private void assertHasAllTheRelevantProperties(UserSearchPreferencesDto userSearchPreferencesDto) {
        assertEquals(A_VALUE_FOR_AIRVIVANT_SEARCH, userSearchPreferencesDto.hasMostlySearchedForAirVivantFlights);
        assertEquals(A_VALUE_FOR_ECONOMIC_SEARCH, userSearchPreferencesDto.hasMostlySearchedForEconomyClassFlights);
        assertEquals(A_VALUE_FOR_BUSINESS_SEARCH, userSearchPreferencesDto.hasMostlySearchedForBusinessClassFlights);
        assertEquals(A_VALUE_FOR_REGULAR_SEARCH, userSearchPreferencesDto.hasMostlySearchedForRegularClassFlights);
    }

    private void assertHasAllTheRelevantProperties(UserSettings userSettings) {
        assertEquals(userSettings.userWantsToHideWeightFilteredAlert(), A_HIDE_WEIGHT_FILTER_VALUE);
    }
}