package com.marshmallow.platform.core.navigator;

import com.marshmallow.platform.core.common.Area;
import com.marshmallow.platform.core.common.Direction;
import com.marshmallow.platform.core.common.Position;
import com.marshmallow.platform.core.common.exceptions.MoveException;
import com.marshmallow.platform.core.robot.MarshmallowRobot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarshmallowNavigatorService implements NavigatorService {

    @Override
    public NavigatorResponse clean(Area area, Position initialPosition, List<Position> affectedArea, List<Direction> directions) throws MoveException {
        List<List<Boolean>> workingField = calculateField(area, affectedArea);
        MarshmallowRobot robot = new MarshmallowRobot(workingField, initialPosition);

        int cleanCounter = 0;

        if(robot.clean()) {
            cleanCounter++;
        }

        for (Direction direction : directions) {
            robot.move(direction);
            if(robot.clean()) {
                cleanCounter++;
            }
        }

        return new NavigatorResponse(robot.getCurrentPosition(), cleanCounter);
    }

    private List<List<Boolean>> calculateField(Area area, List<Position> affectedArea) {
        List<List<Boolean>> field = new ArrayList<>();
        for (int x = 0; x < area.getLength(); x++) {
            List<Boolean> cols = new ArrayList<>();
            for (int y = 0; y < area.getWidth(); y++) {
                if(affectedArea.contains(new Position(x, y))) {
                    cols.add(y,  true);
                } else {
                    cols.add(y, false);
                }
            }
            field.add(cols);
        }
        return field;
    }
}
