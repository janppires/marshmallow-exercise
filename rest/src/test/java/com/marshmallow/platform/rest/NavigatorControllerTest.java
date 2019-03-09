package com.marshmallow.platform.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marshmallow.platform.core.common.Area;
import com.marshmallow.platform.core.common.Direction;
import com.marshmallow.platform.core.common.Position;
import com.marshmallow.platform.core.common.exceptions.MoveException;
import com.marshmallow.platform.core.navigator.MarshmallowNavigatorService;
import com.marshmallow.platform.core.navigator.NavigatorResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

import static java.util.Arrays.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(NavigatorController.class)
class NavigatorControllerTest {

    private static final String CLEAN_URL_TEMPLATE = "/navigator/clean";

    private static final CleanSettingsRequest CLEAN_SETTINGS_REQUEST = createCleanSettingsRequest();
    private static final Area AREA = new Area(CLEAN_SETTINGS_REQUEST.getAreaSize().get(0), CLEAN_SETTINGS_REQUEST.getAreaSize().get(1));
    private static final Position INITIAL_POSITION = new Position(
            CLEAN_SETTINGS_REQUEST.getStartingPosition().get(0),
            CLEAN_SETTINGS_REQUEST.getStartingPosition().get(1)
    );

    private static final List<Position> AFFECTED_AREA = asList(
            new Position(1, 0),
            new Position(2, 2),
            new Position(2, 3)
    );

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LocalValidatorFactoryBean validator;

    @MockBean
    private MarshmallowNavigatorService navigatorService;

    @Test
    void whenCleanThenShouldCallNavigatorCleanServiceAsExpected() throws Exception {
        // Arrange
        List<Direction> directions = asList(
            Direction.NORTH,
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.EAST,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST,
            Direction.NORTH,
            Direction.WEST,
            Direction.WEST
        );
        when(
            navigatorService.clean(AREA, INITIAL_POSITION, AFFECTED_AREA, directions)
        ).thenReturn(new NavigatorResponse(new Position(1,2), 3));

        MockHttpServletRequestBuilder postBuilder =
            post(CLEAN_URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(toJson(CLEAN_SETTINGS_REQUEST));

        // Act
        mvc
            .perform(postBuilder)
            .andExpect(status().isOk())
            .andDo(print());

        // Assert
        verify(navigatorService).clean(
            AREA,
            INITIAL_POSITION,
            AFFECTED_AREA,
                directions
        );
    }

    @Test
    void givenInvalidDirectionsWhenCleanThenShouldReturnBadRequest() throws Exception {
        // Arrange
        List<Direction> invalidDirections = asList(
                Direction.NORTH,
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.EAST,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST,
                Direction.NORTH,
                Direction.WEST,
                Direction.WEST,
                Direction.WEST,
                Direction.WEST,
                Direction.WEST,
                Direction.WEST
        );
        when(
            navigatorService.clean(AREA, INITIAL_POSITION, AFFECTED_AREA, invalidDirections)
        ).thenThrow(new MoveException("Opsss.."));

        MockHttpServletRequestBuilder postBuilder =
            post(CLEAN_URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(toJson(createCleanSettingsRequest("NNESEESWNWWWWWW")));

        // Act
        mvc
            .perform(postBuilder)
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    private static CleanSettingsRequest createCleanSettingsRequest() {
        return createCleanSettingsRequest("NNESEESWNWW");
    }

    private static CleanSettingsRequest createCleanSettingsRequest(String navigationInstructions) {
        List<Integer> areaSize = asList(5, 5);
        List<Integer> startingPosition = asList(1, 2);
        List<List<Integer>> oilPatches = asList(asList(1, 0), asList(2, 2), asList(2, 3));
        return new CleanSettingsRequest(
                areaSize,
                startingPosition,
                oilPatches,
                navigationInstructions
        );
    }

    private String toJson(CleanSettingsRequest request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }
}
