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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CleanResponse {
    private List<Integer> finalPosition;
    private Integer oilPatchesCleaned;
}


