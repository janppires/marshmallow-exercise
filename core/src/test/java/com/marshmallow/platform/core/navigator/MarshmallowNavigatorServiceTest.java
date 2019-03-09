package com.marshmallow.platform.core.navigator;

import com.marshmallow.platform.core.common.Area;
import com.marshmallow.platform.core.common.Direction;
import com.marshmallow.platform.core.common.Position;
import com.marshmallow.platform.core.common.exceptions.MoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class MarshmallowNavigatorServiceTest {

    private static final List<Position> AFFECTED_AREA = asList(
            new Position(1, 0),
            new Position(2, 2),
            new Position(2, 3)
    );

    private static final Position INITIAL_POSITION = new Position(
            1,
            2
    );

    private static final Area AREA = new Area(5, 5);

    private MarshmallowNavigatorService marshmallowNavigatorService;

    @BeforeEach
    void setUp() {
        marshmallowNavigatorService = new MarshmallowNavigatorService();
    }

    @ParameterizedTest
    @MethodSource("provideValidMoves")
    void givenDirectionsWhenCleanThenShouldReturnExpectedResponse(List<Direction> directions, NavigatorResponse expectedResponse) throws MoveException {
        // Act
        NavigatorResponse response = marshmallowNavigatorService.clean(
                AREA,
                INITIAL_POSITION,
                AFFECTED_AREA,
                directions
        );
        // Assert
        assertThat(response).isEqualTo(expectedResponse);
    }

    static Stream<Arguments> provideValidMoves() {
        return Stream.of(
            Arguments.of(
                asList(
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
                ),
                new NavigatorResponse(new Position(1, 3), 1)
            ),
            Arguments.of(
                asList(
                    Direction.SOUTH,
                    Direction.SOUTH,
                    Direction.EAST,
                    Direction.EAST,
                    Direction.EAST
                ),
                new NavigatorResponse(new Position(4, 0), 1)
            ),
            Arguments.of(
                asList(
                    Direction.SOUTH,
                    Direction.SOUTH,
                    Direction.EAST,
                    Direction.NORTH,
                    Direction.NORTH,
                    Direction.NORTH
                ),
                new NavigatorResponse(new Position(2, 3), 3)
            )
        );
    }
}