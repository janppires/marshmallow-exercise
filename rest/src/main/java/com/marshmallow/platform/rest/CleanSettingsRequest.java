package com.marshmallow.platform.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marshmallow.platform.core.common.Area;
import com.marshmallow.platform.core.common.Direction;
import com.marshmallow.platform.core.common.Position;
import com.marshmallow.platform.rest.validators.NavigationInstructions;
import com.marshmallow.platform.rest.validators.OilPatches;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CleanSettingsRequest {
    @Size(min = 2, max = 2, message="Area size must have two dimensions")
    private List<Integer> areaSize;

    @Size(min = 2, max = 2, message="Starting position must have two coordinates")
    private List<Integer> startingPosition;

    @OilPatches
    private List<List<Integer>> oilPatches;

    @NavigationInstructions
    private String navigationInstructions;

    @JsonIgnore
    public Area getArea() {
        return new Area(areaSize.get(0), areaSize.get(1));
    }

    @JsonIgnore
    public Position getInitialPosition() {
        return new Position(startingPosition.get(0), startingPosition.get(1));
    }

    @JsonIgnore
    public List<Position> getAffectedArea() {
        return oilPatches
                .stream()
                .map(coordinates -> new Position(coordinates.get(0), coordinates.get(1)))
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Direction> getNavigationDirections() {
        return navigationInstructions
                .chars()
                .mapToObj(c -> Direction.findByChar((char) c))
                .collect(Collectors.toList());
    }
}


