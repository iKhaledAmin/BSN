package com.khaled_amin.book_social_network.identity.capability.api.contoller;

import com.khaled_amin.book_social_network.core.api.ApiResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import com.khaled_amin.book_social_network.identity.capability.api.dto.CapabilityResponse;
import com.khaled_amin.book_social_network.identity.capability.api.mapper.CapabilityMapper;
import com.khaled_amin.book_social_network.identity.capability.application.port.CapabilityService;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("capabilities")
@RequiredArgsConstructor
public class CapabilityController {

    private final CapabilityService capabilityService;
    private final CapabilityMapper capabilityMapper;



    @PreAuthorize("hasAuthority('capability_read')")
    @GetMapping("/{code}")
    public ResponseEntity<ApiResponse<CapabilityResponse>> getByCode(@PathVariable String code) {

        Capability capability = capabilityService.viewCapability(CapabilityCode.of(code));

        CapabilityResponse response = capabilityMapper.toResponse(capability);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @PreAuthorize("hasAuthority('capability_read')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CapabilityResponse>>> getAll(
            @RequestParam(required = false) SystemDomain module) {

        List<Capability> capabilities = capabilityService.listCapabilities(module);

        List<CapabilityResponse> response = capabilities
                .stream()
                .map(capabilityMapper::toResponse).
                toList();

        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }


}