package ca.ulaval.glo4003.ws.api.weightDetection;

import ca.ulaval.glo4003.ws.api.flight.FlightResource;
import ca.ulaval.glo4003.ws.api.flight.FlightResourceImpl;
import ca.ulaval.glo4003.ws.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.ws.api.weightDetection.dto.WeightDetectionDto;
import ca.ulaval.glo4003.ws.domain.flight.FlightService;
import ca.ulaval.glo4003.ws.domain.weightDetection.WeightDetectionService;
import jersey.repackaged.com.google.common.collect.Lists;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.WebApplicationException;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WeightDetectionResourceImplTest {
    private final static WeightDetectionDto A_WEIGHT_DTO = new WeightDetectionDto();

    @Mock
    private WeightDetectionService weightDetectionService;

    private WeightDetectionResource weightDetectionResourceImpl;

    @Before
    public void setup() {
        weightDetectionResourceImpl = new WeightDetectionResourceImpl(weightDetectionService);
    }

    @Test
    public void givenAWeightToDetect_whenDetectingWeight_thenItsDelegatedToTheService() {
        given(weightDetectionService.detectWeight()).willReturn(A_WEIGHT_DTO);

        WeightDetectionDto result = weightDetectionResourceImpl.detectWeight();

        assertEquals(result, A_WEIGHT_DTO);
    }
}