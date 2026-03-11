package com.khaled_amin.book_social_network.role.controller;

import com.khaled_amin.book_social_network.common.dto.ApiResponse;
import com.khaled_amin.book_social_network.common.response.ApiResponseFactory;
import com.khaled_amin.book_social_network.common.servise.EntityRetrievalService;
import com.khaled_amin.book_social_network.role.model.dto.RoleRequest;
import com.khaled_amin.book_social_network.role.model.dto.RoleResponse;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import com.khaled_amin.book_social_network.role.model.mapper.RoleMapper;
import com.khaled_amin.book_social_network.role.service.RoleService;
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
            @Valid @RequestBody RoleRequest request) {

        Role role = roleService.add(request);

        RoleResponse response = roleMapper.toResponse(role);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseFactory.success(response));
    }

    @Operation(summary = "Update an existing role")
    @PutMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(
            @PathVariable Long roleId,
            @Valid @RequestBody RoleRequest request) {

        Role role = roleService.update(roleId, request);

        RoleResponse response = roleMapper.toResponse(role);

        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @Operation(summary = "Get role by id")
    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRole(
            @PathVariable Long roleId) {

        Role role = roleService.getById(roleId);

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