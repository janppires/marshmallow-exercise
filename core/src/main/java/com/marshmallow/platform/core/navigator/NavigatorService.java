package com.marshmallow.platform.core.navigator;

import com.marshmallow.platform.core.common.Area;
import com.marshmallow.platform.core.common.Direction;
import com.marshmallow.platform.core.common.Position;
import com.marshmallow.platform.core.common.exceptions.MoveException;

import java.util.List;

public interface NavigatorService {
    NavigatorResponse clean(Area area, Position initialPosition, List<Position> affectedArea, List<Direction> directions) throws MoveException;
}
