package com.marshmallow.platform.rest;

import com.marshmallow.platform.core.common.exceptions.MoveException;
import com.marshmallow.platform.core.navigator.NavigatorResponse;
import com.marshmallow.platform.core.navigator.NavigatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/navigator")
public class NavigatorController {

    @Autowired
    private NavigatorService navigatorService;

    @PostMapping("/clean")
    public CleanResponse clean(@Valid @RequestBody CleanSettingsRequest cleanSettingsRequest) {
        NavigatorResponse response = doClean(cleanSettingsRequest);

        List<Integer> finalPosition = asList(
                response.getFinalPosition().getX(),
                response.getFinalPosition().getY()
        );
        return new CleanResponse(finalPosition, response.getPositionsCleaned());
    }

    private NavigatorResponse doClean(CleanSettingsRequest cleanSettingsRequest) {
        try {
            return navigatorService.clean(
                    cleanSettingsRequest.getArea(),
                    cleanSettingsRequest.getInitialPosition(),
                    cleanSettingsRequest.getAffectedArea(),
                    cleanSettingsRequest.getNavigationDirections()
            );
        } catch (MoveException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, getErrorMessage(e), e);
        }
    }

    private String getErrorMessage(Exception e) {
        if(e.getCause() != null) {
            return String.format("%s: %s", e.getMessage(), e.getCause().getMessage());
        }
        return e.getMessage();
    }
}
