package com.khaled_amin.book_social_network.identity.user.role.api.controller;

import com.khaled_amin.book_social_network.core.api.ActionResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleCreateRequest;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleResponse;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleUpdateRequest;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.api.mapper.RoleMapper;
import com.khaled_amin.book_social_network.identity.user.role.application.service.RoleService;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('role_create')")
    public ResponseEntity<ApiResponse<RoleResponse>> create(@Valid @RequestBody RoleCreateRequest request) {

        Role role = roleService.createBusinessRole(request);
        RoleResponse response =roleMapper.toResponse(role);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                    ApiResponseFactory.success(response)
                );
    }

    @PatchMapping("/{roleName}")
    @PreAuthorize("hasAuthority('role_update')")
    public ResponseEntity<ApiResponse<RoleResponse>> update(@PathVariable String roleName, @Valid @RequestBody RoleUpdateRequest request) {

        Role role = roleService.update(
                RoleName.of(roleName),
                request
        );
        RoleResponse response = roleMapper.toResponse(role);

        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @DeleteMapping("/{roleName}")
    @PreAuthorize("hasAuthority('role_delete')")
    public ResponseEntity<ApiResponse<ActionResponse>> delete(@PathVariable String roleName) {

        roleService.delete(RoleName.of(roleName));
        ActionResponse response = ActionResponse
                .builder()
                .message("Role deleted successfully")
                .build();

        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @GetMapping("/{roleName}")
    @PreAuthorize("hasAuthority('role_read')")
    public ResponseEntity<ApiResponse<RoleResponse>> get(@PathVariable String roleName) {

        Role role = roleService.viewRole(
                RoleName.of(roleName)
        );
        RoleResponse response = roleMapper.toResponse(role);

        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }


    @GetMapping
    @PreAuthorize("hasAuthority('role_read')")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAll() {
        List<Role> roles = roleService.listRoles();

        List<RoleResponse> responses = roles
                .stream()
                .map(roleMapper::toResponse)
                .toList();

        return ResponseEntity.ok(
                ApiResponseFactory.success(responses)
        );
    }

    @PutMapping("/{roleName}/capabilities/{capabilityCode}")
    @PreAuthorize("hasAuthority('role_add_capability')")
    public ResponseEntity<ApiResponse<RoleResponse>> addCapability(@PathVariable String roleName, @PathVariable String capabilityCode) {

        Role role = roleService.addCapability(
                RoleName.of(roleName),
                CapabilityCode.of(capabilityCode)
        );
        RoleResponse response =  roleMapper.toResponse(role);

        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @DeleteMapping("/{roleName}/capabilities/{capabilityCode}")
    @PreAuthorize("hasAuthority('role_remove_capability')")
    public ResponseEntity<ApiResponse<RoleResponse>> removeCapability(@PathVariable String roleName, @PathVariable String capabilityCode) {

        Role role = roleService.removeCapability(
                RoleName.of(roleName),
                CapabilityCode.of(capabilityCode)
        );
        RoleResponse response = roleMapper.toResponse(role);

        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }
}