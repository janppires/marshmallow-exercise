package com.marshmallow.platform.core.robot;

import com.marshmallow.platform.core.common.Direction;
import com.marshmallow.platform.core.common.Position;
import com.marshmallow.platform.core.common.exceptions.MoveException;

public interface Robot {

    boolean clean();

    void move(Direction direction) throws MoveException;

    Position getCurrentPosition();
}
