package com.marshmallow.platform;

import com.jayway.jsonpath.JsonPath;
import com.marshmallow.platform.rest.CleanSettingsRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MarshmallowPlatformApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InvalidNavigationInstructionsTest {

    private static final String CLEAN_URL_TEMPLATE = "/navigator/clean";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @ParameterizedTest
    @MethodSource("provideInvalidCleanSettings")
    void givenInvalidArgumentsWhenCleanThenShouldReturnExpectedError(CleanSettingsRequest cleanSettingsRequest, String expectedErrorMessage) {
        // Act
        ResponseEntity<String> stringResponseEntity = testRestTemplate.postForEntity(
                CLEAN_URL_TEMPLATE,
                cleanSettingsRequest,
                String.class
        );

        // Assert
        assertThat(
            JsonPath.<String>read(
                stringResponseEntity.getBody(),
                "$.errors[0].defaultMessage"
            )
        )
        .isEqualTo(expectedErrorMessage);
    }

    static Stream<Arguments> provideInvalidCleanSettings() {
        return Stream.of(
                Arguments.of(
                        createCleanSettingsRequestWithInvalidAreaSize(),
                        "Area size must have two dimensions"
                ),
                Arguments.of(
                        createCleanSettingsRequestWithInvalidStartingPosition(),
                        "Starting position must have two coordinates"
                ),
                Arguments.of(
                        createCleanSettingsRequestWithNullOilPatches(),
                        "Oil patches cannot be null"
                ),
                Arguments.of(
                        createCleanSettingsRequestWithInvalidOilPatches(),
                        "Oil patches coordinates must be in the format x,y"
                ),
                Arguments.of(
                        createCleanSettingsRequestWithInvalidNavigationIntructions(),
                        "Navigation instructions only allows: N, E, S, W"
                )
        );
    }

    private static CleanSettingsRequest createCleanSettingsRequestWithInvalidAreaSize() {
        List<Integer> areaSize = asList(5);
        List<Integer> startingPosition = asList(1, 2);
        List<List<Integer>> oilPatches = asList(asList(1, 0), asList(2, 2), asList(2, 3));
        String navigationInstructions = "NNESEESWNWW";
        return new CleanSettingsRequest(
                areaSize,
                startingPosition,
                oilPatches,
                navigationInstructions
        );
    }

    private static CleanSettingsRequest createCleanSettingsRequestWithInvalidStartingPosition() {
        List<Integer> areaSize = asList(5, 5);
        List<Integer> startingPosition = asList(1, 2, 4);
        List<List<Integer>> oilPatches = asList(asList(1, 0), asList(2, 2), asList(2, 3));
        String navigationInstructions = "NNESEESWNWW";
        return new CleanSettingsRequest(
                areaSize,
                startingPosition,
                oilPatches,
                navigationInstructions
        );
    }

    private static CleanSettingsRequest createCleanSettingsRequestWithNullOilPatches() {
        List<Integer> areaSize = asList(5, 5);
        List<Integer> startingPosition = asList(1, 2);
        List<List<Integer>> oilPatches = null;
        String navigationInstructions = "NNESEESWNWW";
        return new CleanSettingsRequest(
                areaSize,
                startingPosition,
                oilPatches,
                navigationInstructions
        );
    }

    private static CleanSettingsRequest createCleanSettingsRequestWithInvalidOilPatches() {
        List<Integer> areaSize = asList(5, 5);
        List<Integer> startingPosition = asList(1, 2);
        List<List<Integer>> oilPatches = asList(asList(1, 0), asList(2, 2, 4), asList(2, 3));
        String navigationInstructions = "NNESEESWNWW";
        return new CleanSettingsRequest(
                areaSize,
                startingPosition,
                oilPatches,
                navigationInstructions
        );
    }

    private static CleanSettingsRequest createCleanSettingsRequestWithInvalidNavigationIntructions() {
        List<Integer> areaSize = asList(5, 5);
        List<Integer> startingPosition = asList(1, 2);
        List<List<Integer>> oilPatches = asList(asList(1, 0), asList(2, 2), asList(2, 3));
        String navigationInstructions = "NNRSWE";
        return new CleanSettingsRequest(
                areaSize,
                startingPosition,
                oilPatches,
                navigationInstructions
        );
    }
}
