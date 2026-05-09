package com.khaled_amin.book_social_network.identity.user.role.api.controller;

import com.khaled_amin.book_social_network.core.api.ApiResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.CreateRoleRequest;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleResponse;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.UpdateRoleRequest;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.CreateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.UpdateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.api.mapper.RoleMapper;
import com.khaled_amin.book_social_network.identity.user.role.application.service.RoleService;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(
            @Valid @RequestBody CreateRoleRequest request) {

        CreateRoleCommand createCommand = roleMapper.toCommand(request);
        Role role = roleService.createBusinessRole(createCommand);

        RoleResponse response = roleMapper.toResponse(role);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseFactory.success(response));
    }

    @Operation(summary = "Update an existing role")
    @PutMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(
            @PathVariable Long roleId,
            @Valid @RequestBody UpdateRoleRequest request) {

        UpdateRoleCommand updateCommand = roleMapper.toCommand(request);
        Role role = roleService.update(roleId, updateCommand);

        RoleResponse response = roleMapper.toResponse(role);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Long roleId) {

        roleService.delete(RoleId.of(roleId));

        return ResponseEntity.ok(
                ApiResponseFactory.success(null)
        );
    }

    @Operation(summary = "Get role by id")
    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRole(
            @PathVariable Long roleId) {

        Role role = roleService.getById(RoleId.of(roleId));

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



}