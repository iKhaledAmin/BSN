package com.khaled_amin.book_social_network.identity.capability.api.contoller;

import com.khaled_amin.book_social_network.core.api.ApiResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import com.khaled_amin.book_social_network.identity.capability.api.dto.CapabilityResponse;
import com.khaled_amin.book_social_network.identity.capability.api.dto.CapabilityUpdateRequest;
import com.khaled_amin.book_social_network.identity.capability.api.mapper.CapabilityMapper;
import com.khaled_amin.book_social_network.identity.capability.application.port.CapabilityService;
import com.khaled_amin.book_social_network.identity.capability.domain.command.CapabilityUpdateCommand;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.capability.domain.model.CapabilityModule;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("capabilities")
@RequiredArgsConstructor
@Tag(name = "Capability Management")
public class CapabilityController {

    private final CapabilityService capabilityService;
    private final CapabilityMapper capabilityMapper;


    @Operation(summary = "Update capabilities metadata")
    @PutMapping("/{code}")
    public ResponseEntity<ApiResponse<CapabilityResponse>> update(@PathVariable String code, @Valid @RequestBody CapabilityUpdateRequest request) {

        CapabilityUpdateCommand command = capabilityMapper.toCommand(request);

        Capability capability = capabilityService.update(
                CapabilityCode.of(code),
                command
        );

        CapabilityResponse response = capabilityMapper.toResponse(capability);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @Operation(summary = "Get capabilities by code")
    @GetMapping("/{code}")
    public ResponseEntity<ApiResponse<CapabilityResponse>> getByCode(@PathVariable String code) {

        Capability capability = capabilityService.getByCode(CapabilityCode.of(code));

        CapabilityResponse response = capabilityMapper.toResponse(capability);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @Operation(summary = "Get all capabilities")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CapabilityResponse>>> getAll(
            @RequestParam(required = false) CapabilityModule module
    ) {

        List<Capability> capabilities =
                module == null ? capabilityService.getAll() : capabilityService.getByModule(module);

        List<CapabilityResponse> response = capabilities
                .stream()
                .map(capabilityMapper::toResponse).
                toList();

        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }


}