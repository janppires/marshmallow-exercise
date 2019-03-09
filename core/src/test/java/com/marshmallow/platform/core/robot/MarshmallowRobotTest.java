package com.marshmallow.platform.core.robot;

import com.marshmallow.platform.core.common.Direction;
import com.marshmallow.platform.core.common.Position;
import com.marshmallow.platform.core.common.exceptions.MoveException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class MarshmallowRobotTest {

    @ParameterizedTest
    @MethodSource("provideValidInitialPosition")
    void givenInitialPositionWhenCleanThenShouldCleanAsExpected(Position initialCleanPosition, boolean expectedClean) {
        // Arrange
        MarshmallowRobot marshmallowRobot = new MarshmallowRobot(createField(), initialCleanPosition);
        // Act
        boolean cleaned = marshmallowRobot.clean();
        // Assert
        assertThat(cleaned).isEqualTo(expectedClean);
        // second clean must always be false
        assertFalse(marshmallowRobot.clean());
    }

    static Stream<Arguments> provideValidInitialPosition() {
        return Stream.of(
                Arguments.of(
                        new Position(1, 2),
                        false
                ),
                Arguments.of(
                        new Position(1, 0),
                        true
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMoves")
    void givenInvalidMovesWhenMoveThenShouldThrowMoveException(List<Direction> directions) throws MoveException {
        // Arrange
        Position initialCleanPosition = new Position(1, 2);
        MarshmallowRobot marshmallowRobot = new MarshmallowRobot(createField(), initialCleanPosition);

        for (int i = 0; i < directions.size(); i++) {
            Direction direction = directions.get(i);
            marshmallowRobot.move(direction);

            if(i == directions.size() - 2) {
                // Act & Assert
                Exception exception = assertThrows(MoveException.class, () -> marshmallowRobot.move(direction));
                assertEquals("Moving beyond designated area", exception.getMessage());
                break;
            }
        }
    }

    static Stream<Arguments> provideInvalidMoves() {
        return Stream.of(
                Arguments.of(
                        asList(Direction.EAST, Direction.EAST, Direction.EAST, Direction.EAST)
                ),
                Arguments.of(
                        asList(Direction.WEST, Direction.WEST)
                ),
                Arguments.of(
                        asList(Direction.NORTH, Direction.NORTH, Direction.NORTH)
                ),
                Arguments.of(
                        asList(Direction.SOUTH, Direction.SOUTH, Direction.SOUTH)
                )
        );
    }

    private List<List<Boolean>> createField() {
        return asList(
                createColumns(
                        false, false, false, false, false
                ),
                createColumns(
                        true, false, false, false, false
                ),
                createColumns(
                        false, false, true, true, false
                ),
                createColumns(
                        false, false, false, false, false
                ),
                createColumns(
                        false, false, false, false, false
                )
        );
    }

    private ArrayList<Boolean> createColumns(Boolean...columns) {
        return new ArrayList<>(asList(columns));
    }
}