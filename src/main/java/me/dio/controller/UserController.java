package me.dio.controller;

import me.dio.domain.model.User;
import me.dio.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }//indica que depende do userService

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        var user = userService.findById(id); //"var": permite declarar variáveis locais sem precisar especificar o tipo de dado explicitamente
        return ResponseEntity.ok(user);
    }//interessante criar DTO's (UserDTO) para expor como saída somente as informações que julgar necessárias

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User userToCreate) {
        var userCreated = userService.create(userToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();//para retornar o status 201 (created) e o location do recurso criado
        return ResponseEntity.created(location).body(userCreated);
    }
}