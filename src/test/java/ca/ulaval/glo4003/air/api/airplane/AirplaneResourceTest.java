package ca.ulaval.glo4003.air.api.airplane;

import ca.ulaval.glo4003.air.transfer.airplane.dto.AirplaneDto;
import ca.ulaval.glo4003.air.transfer.airplane.dto.AirplaneSearchResultDto;
import ca.ulaval.glo4003.air.transfer.airplane.dto.AirplaneUpdateDto;
import ca.ulaval.glo4003.air.domain.airplane.AirplaneNotFoundException;
import ca.ulaval.glo4003.air.domain.user.InvalidTokenException;
import ca.ulaval.glo4003.air.domain.user.UnauthorizedException;
import ca.ulaval.glo4003.air.service.airplane.AirplaneService;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.WebApplicationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AirplaneResourceTest {
    private static final boolean NEEDS_TO_BE_AIR_LOURD = true;
    private static final String A_SERIAL_NUMBER = "keep me in mind - zac brown band";
    private static final String AN_INVALID_TOKEN = "rock me mamma - darius rucker";
    private static final String A_TOKEN = "toes, knee deep, chicken fried, etc.";
    private static final String AN_INVALID_SERIAL_NUMBER = "alright, don't think I don't think about it, etc.";

    @Mock
    private AirplaneSearchResultDto airplaneSearchResultDto;

    @Mock
    private AirplaneUpdateDto airplaneUpdateDto;

    @Mock
    private AirplaneDto airplaneDto;

    @Mock
    private AirplaneService airplaneService;

    private AirplaneResource airplaneResource;

    @Before
    public void setUp() throws Exception {
        airplaneResource = new AirplaneResource(airplaneService);
    }

    @Test
    public void whenFindingAllAirplanesWithFilters_thenCorrespondingAirplanesAreReturned() {
        given(airplaneService.findAllWithFilters(NEEDS_TO_BE_AIR_LOURD)).willReturn(airplaneSearchResultDto);

        AirplaneSearchResultDto searchResult = airplaneResource.findAllWithFilters(NEEDS_TO_BE_AIR_LOURD);

        assertEquals(airplaneSearchResultDto, searchResult);
    }

    @Test
    public void whenUpdatingAnAirplane_thenUpdatedAirplaneIsReturned() throws Exception {
        given(airplaneService.updateAirplane(A_TOKEN, A_SERIAL_NUMBER, airplaneUpdateDto)).willReturn(airplaneDto);

        AirplaneDto updateResult = airplaneResource.update(airplaneUpdateDto, A_SERIAL_NUMBER, A_TOKEN);

        assertEquals(airplaneDto, updateResult);
    }

    @Test
    public void givenAnInvalidToken_whenUpdatingAnAirplane_then401IsThrown() throws Exception {
        given(airplaneService.updateAirplane(AN_INVALID_TOKEN, A_SERIAL_NUMBER, airplaneUpdateDto)).willThrow(InvalidTokenException.class);

        try {
            airplaneResource.update(airplaneUpdateDto, A_SERIAL_NUMBER, AN_INVALID_TOKEN);
            fail("Exception not thrown");
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.UNAUTHORIZED_401)));
        }
    }

    @Test
    public void givenAnUnauthorizedUser_whenUpdatingAnAirplane_then401IsThrown() throws Exception {
        given(airplaneService.updateAirplane(A_TOKEN, A_SERIAL_NUMBER, airplaneUpdateDto)).willThrow(UnauthorizedException.class);

        try {
            airplaneResource.update(airplaneUpdateDto, A_SERIAL_NUMBER, A_TOKEN);
            fail("Exception not thrown");
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.UNAUTHORIZED_401)));
        }
    }

    @Test
    public void givenANonExistentAirplane_whenUpdatingAnAirplane_then404IsThrown() throws Exception {
        given(airplaneService.updateAirplane(A_TOKEN, AN_INVALID_SERIAL_NUMBER, airplaneUpdateDto)).willThrow(AirplaneNotFoundException.class);

        try {
            airplaneResource.update(airplaneUpdateDto, AN_INVALID_SERIAL_NUMBER, A_TOKEN);
            fail("Exception not thrown");
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.NOT_FOUND_404)));
        }
    }
}