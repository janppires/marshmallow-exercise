package com.marshmallow.platform.core.robot;

import com.marshmallow.platform.core.common.Direction;
import com.marshmallow.platform.core.common.Position;
import com.marshmallow.platform.core.common.exceptions.MoveException;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class MarshmallowRobot implements Robot {
    private final List<List<Boolean>> field;
    private final Position initialPosition;
    private Position currentPosition;

    public MarshmallowRobot(List<List<Boolean>> field, Position initialPosition) {
        this.field = field;
        this.initialPosition = initialPosition;
        this.currentPosition = initialPosition;
    }

    /** Cleans the current position
     *
     * @return Returns true if has dirt to clean on the current position, otherwise returns false
     * */
    @Override
    public boolean clean() {
        boolean isDirty = field.get(currentPosition.getX()).get(currentPosition.getY());
        if(isDirty) {
            field.get(currentPosition.getX()).add(currentPosition.getY(), false);
            return true;
        }
        return false;
    }

    @Override
    public void move(Direction direction) throws MoveException {
        Position newPosition = getNewPosition(direction);

        if(isOutsideArea(newPosition)) {
            throw new MoveException("Moving beyond designated area");
        }
        currentPosition = newPosition;
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    private Position getNewPosition(Direction direction) {
        switch (direction) {
            case NORTH:
                return new Position(currentPosition.getX(), currentPosition.getY() + 1);
            case EAST:
                return new Position(currentPosition.getX() + 1, currentPosition.getY());
            case WEST:
                return new Position(currentPosition.getX() - 1, currentPosition.getY());
            case SOUTH:
                return new Position(currentPosition.getX(), currentPosition.getY() - 1);
        }
        throw new IllegalStateException("Unknown direction: " + direction);
    }

    private boolean isOutsideArea(Position position) {
        int length = field.size();
        int width = field.get(0).size();

        return position.getX() < 0
            || position.getY() < 0
            || position.getX() >= length
            || position.getY() >= width;
    }
}
