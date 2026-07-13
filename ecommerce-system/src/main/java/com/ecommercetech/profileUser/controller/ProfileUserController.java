package com.ecommercetech.profileUser.controller;

import com.ecommercetech.profileUser.dto.ProfileUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommercetech.profileUser.service.ProfileUserService;
import com.ecommercetech.profileUser.dto.ProfileUserRequestDTO;

import java.util.List;


@RestController
@RequestMapping("/api/profileUser")
public class ProfileUserController {

    @Autowired
    private ProfileUserService profileUserService;

    @PostMapping
    public ResponseEntity<ProfileUserResponseDTO> createProfileUser(@RequestBody ProfileUserRequestDTO profileUserRequestDTO) {
        return ResponseEntity.ok(profileUserService.createProfileUser(profileUserRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<ProfileUserResponseDTO>> getAllProfileUser() {
        return ResponseEntity.ok(profileUserService.getAllProfileUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileUserResponseDTO> getProfileUserById(@PathVariable Long id) {
        return ResponseEntity.ok(profileUserService.getProfileUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileUserResponseDTO> updateProfileUser(@RequestBody ProfileUserRequestDTO profileUserRequestDTO, @PathVariable Long id) {
        return ResponseEntity.ok(profileUserService.updateProfileUser(profileUserRequestDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfileUser(@PathVariable Long id) {
        profileUserService.deleteProfileUser(id);
        return ResponseEntity.noContent().build();
    }
}
