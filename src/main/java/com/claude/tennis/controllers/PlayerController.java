package com.claude.tennis.controllers;

// import java.util.Collections;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.claude.tennis.dto.PlayerDto;
import com.claude.tennis.dto.PlayerToSave;
import com.claude.tennis.services.PlayerService;

@Tag(name = "Tennis Players API")
@RestController
@RequestMapping("/players")
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Operation(summary = "Finds players", description = "Finds players", security = {
            @SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players list", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PlayerDto.class))) })
    })

    @GetMapping
    public List<PlayerDto> list() {
        return playerService.getAllPlayers();
    }

    @Operation(summary = "Finds a player", description = "Finds a player", security = {
            @SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDto.class)) }),

            @ApiResponse(responseCode = "404", description = "Player not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)) }),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action.")
    })

    @GetMapping("{lastName}")
    public PlayerDto getByLastName(@PathVariable("lastName") String lastName) {
        return playerService.getByLastName(lastName);
    }

    @Operation(summary = "Creates a player", description = "Creates a player", security = {
            @SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created player", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerToSave.class)) }),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action.")
    })
    @PostMapping
    public PlayerDto createPlayer(@RequestBody @Valid PlayerToSave playerToRegister) {
        return playerService.create(playerToRegister);
    }

    @Operation(summary = "Updates a player", description = "Updates a player", security = {
            @SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated player", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerToSave.class)) }),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action.")
    })
    @PutMapping
    public PlayerDto updatePlayer(@RequestBody @Valid PlayerToSave playerToSave) {

        return playerService.update(playerToSave);
    }

    @Operation(summary = "Deletes a player", description = "Deletes a player", security = {
            @SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player has been deleted"),
            @ApiResponse(responseCode = "404", description = "Player with specified last name was not found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)) }),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action.")

    })
    @DeleteMapping("{lastName}")
    public void deletePlayerByLastName(@PathVariable("lastName") String lastName) {
        playerService.delete(lastName);
    }
}
