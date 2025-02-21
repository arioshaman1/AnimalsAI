package ru.fomin.auth.security.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.fomin.auth.security.rest.model.UserRequest;
import ru.fomin.auth.security.rest.model.UserResponse;
import ru.fomin.auth.security.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;


    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> createOrder(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.saveUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> updateOrder(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.updateUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        UserResponse userResponse = userService.findUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        UserResponse userResponse = userService.findUserByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<?> findAll(){
        List<UserResponse> userResponses = userService.findAllUsers();
        return ResponseEntity.ok(userResponses);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }










}
