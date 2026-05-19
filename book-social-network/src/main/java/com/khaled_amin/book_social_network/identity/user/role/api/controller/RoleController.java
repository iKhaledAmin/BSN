package com.khaled_amin.book_social_network.identity.user.role.api.controller;

import com.khaled_amin.book_social_network.core.api.ApiResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleAssignCapabilityRequest;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleCreateRequest;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleResponse;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleUpdateRequest;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.CreateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.UpdateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.api.mapper.RoleMapper;
import com.khaled_amin.book_social_network.identity.user.role.application.service.RoleService;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Role Management")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @Operation(summary = "Create a new role")
    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@Valid @RequestBody RoleCreateRequest request) {

        CreateRoleCommand createCommand = roleMapper.toCommand(request);
        Role role = roleService.createBusinessRole(createCommand);

        RoleResponse response = roleMapper.toResponse(role);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseFactory.success(response));
    }

    @Operation(summary = "Update an existing role")
    @PutMapping("/{name}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(@PathVariable String name, @Valid @RequestBody RoleUpdateRequest request) {

        UpdateRoleCommand updateCommand = roleMapper.toCommand(request);
        RoleName roleName = RoleName.of(name);

        Role role = roleService.update(roleName, updateCommand);

        RoleResponse response = roleMapper.toResponse(role);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable String name) {

        roleService.delete(RoleName.of(name));

        return ResponseEntity.ok(
                ApiResponseFactory.success(null)
        );
    }

    @Operation(summary = "Get role by name")
    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRole(@PathVariable String name) {

        Role role = roleService.getByName(RoleName.of(name));

        RoleResponse response = roleMapper.toResponse(role);

        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }


    @Operation(summary = "Get all roles")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {

        List<RoleResponse> roles =
                roleService.getAll()
                        .stream()
                        .map(roleMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(
                ApiResponseFactory.success(roles)
        );
    }


    @Operation(summary = "Assign capability to role")
    @PostMapping("/{name}/capabilities")
    public ResponseEntity<ApiResponse<RoleResponse>> addCapability(
            @PathVariable String name,
            @Valid @RequestBody RoleAssignCapabilityRequest request
    ) {

        Role role = roleService.addCapability(
                RoleName.of(name),
                CapabilityCode.of(request.getCapabilityCode())
        );

        RoleResponse response = roleMapper.toResponse(role);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }


    @Operation(summary = "Remove capability from role")
    @DeleteMapping("/{name}/capabilities/{capabilityCode}")
    public ResponseEntity<ApiResponse<RoleResponse>> removeCapability(
            @PathVariable String name,
            @PathVariable String capabilityCode
    ) {

        Role role = roleService.removeCapability(
                RoleName.of(name),
                CapabilityCode.of(capabilityCode)
        );

        RoleResponse response = roleMapper.toResponse(role);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

}