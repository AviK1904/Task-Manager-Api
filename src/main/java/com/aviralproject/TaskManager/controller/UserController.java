package com.aviralproject.TaskManager.controller;

import com.aviralproject.TaskManager.dto.AddUserRequestdto;
import com.aviralproject.TaskManager.dto.Userdto;
import com.aviralproject.TaskManager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Userdto createUser(@Valid @RequestBody AddUserRequestdto request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public Userdto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public Page<Userdto> getAllUsers(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "5")int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        return userService.getAllUsers(page,size, sortBy,order);
    }

    @PutMapping("/{id}")
    public Userdto updateUser(@PathVariable Long id, @Valid @RequestBody AddUserRequestdto request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

