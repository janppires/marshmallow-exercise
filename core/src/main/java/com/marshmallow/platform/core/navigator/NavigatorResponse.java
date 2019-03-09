package com.marshmallow.platform.core.navigator;

import com.marshmallow.platform.core.common.Position;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class NavigatorResponse {
    private Position finalPosition;
    private int positionsCleaned;
}
