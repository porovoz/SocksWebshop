package com.bestapp.coursework03sockswebshop.controllers;

import com.bestapp.coursework03sockswebshop.model.Color;
import com.bestapp.coursework03sockswebshop.model.Size;
import com.bestapp.coursework03sockswebshop.model.Socks;
import com.bestapp.coursework03sockswebshop.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "Socks", description = "CRUD-operations to work with the socks")
public class SocksController {
private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping
    @Operation(
            summary = "New socks arrival",
            description = "Register new socks arrival"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Socks were successfully registered",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request. Missing or incorrect format request parameters",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )

    })
    public ResponseEntity<Socks> createSocks(@RequestBody Socks socks, @RequestParam long quantity) {
        Socks createdSocks = socksService.addSocks(socks, quantity);
        return ResponseEntity.ok(createdSocks);
    }

    @PutMapping
    @Operation(
            summary = "Sell socks",
            description = "Sell socks from warehouse"
    )
    @Parameters(value = {
            @Parameter(name = "quantity", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Socks were successfully sold",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "There are no socks in stock. Bad request. Missing or incorrect format request parameters",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Socks> editSocks(@RequestBody Socks socks,
                                           @RequestParam long quantity) {
            Socks socks1 = socksService.editSocks(socks, quantity);
            if (socks1 == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(socks1);
    }

    @GetMapping
    @Operation(
            summary = "Find socks quantity by cotton part",
            description = "Show socks quantity by parameter"
    )
    @Parameters(
            value = {
                    @Parameter(
                            name = "cotton min", example = "0"
                    ),
                    @Parameter(
                            name = "cotton max", example = "100"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Socks were successfully found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request. Missing or incorrect format request parameters",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Long> getSocksNumByParam(@RequestParam Color color,
                                                      @RequestParam Size size,
                                                      @RequestParam int cottonMin,
                                                      @RequestParam int cottonMax) {
        long count = socksService.getSocksNumByParam(color, size, cottonMin, cottonMax);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping
    @Operation(
            summary = "Defective socks write-off",
            description = "Register the write-of of defective socks"
    )
    @Parameters(value = {
            @Parameter(name = "quantity", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Defective socks were successfully written-off",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "There are no socks in stock. Bad request. Missing or incorrect format request parameters",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Void> deleteSocks(@RequestBody Socks socks, @RequestParam long quantity) {
        if (socksService.deleteSocks(socks, quantity)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
