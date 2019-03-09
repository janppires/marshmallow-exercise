package com.marshmallow.platform;

import com.jayway.jsonpath.JsonPath;
import com.marshmallow.platform.rest.CleanResponse;
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
class NavigateAndCleanSuccessfulTest {

    private static final String CLEAN_URL_TEMPLATE = "/navigator/clean";

    private static final List<Integer> AREA_SIZE = asList(5, 5);
    private static final List<Integer> STARTING_POSITION = asList(1, 2);
    private static final List<List<Integer>> OIL_PATCHES = asList(asList(1, 0), asList(2, 2), asList(2, 3));

    @Autowired
    private TestRestTemplate testRestTemplate;

    @ParameterizedTest
    @MethodSource("provideValidCleanSettings")
    void givenValidCleanSettingsWhenCleanThenShouldReturnExpectedResponse(
            CleanSettingsRequest cleanSettingsRequest,
            CleanResponse expectedResponse
    ) {
        // Act
        ResponseEntity<CleanResponse> responseEntity = testRestTemplate.postForEntity(
                CLEAN_URL_TEMPLATE,
                cleanSettingsRequest,
                CleanResponse.class
        );

        // Assert
        assertThat(responseEntity.getBody()).isEqualTo(expectedResponse);
    }

    static Stream<Arguments> provideValidCleanSettings() {
        return Stream.of(
                Arguments.of(
                        createCleanSettings("NNESEESWNWW"),
                        new CleanResponse(asList(1, 3), 1)
                ),
                Arguments.of(
                        createCleanSettings("SSEEE"),
                        new CleanResponse(asList(4, 0), 1)
                )
                ,
                Arguments.of(
                        createCleanSettings("SSENNN"),
                        new CleanResponse(asList(2, 3), 3)
                )
        );
    }

    private static CleanSettingsRequest createCleanSettings(String navigationInstructions) {
        return new CleanSettingsRequest(
                AREA_SIZE,
                STARTING_POSITION,
                OIL_PATCHES,
                navigationInstructions
        );
    }
}
