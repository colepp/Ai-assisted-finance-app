package colepp.app.wealthwisebackend.users.controllers;


import colepp.app.wealthwisebackend.auth.dtos.UserLoginResponse;
import colepp.app.wealthwisebackend.common.dtos.JwtResponse;
import colepp.app.wealthwisebackend.common.dtos.ErrorDto;
import colepp.app.wealthwisebackend.users.dtos.RegisterUserRequest;
import colepp.app.wealthwisebackend.users.dtos.UpdateUserRequest;
import colepp.app.wealthwisebackend.users.dtos.UserDto;


import colepp.app.wealthwisebackend.users.excpetions.AccountExistException;
import colepp.app.wealthwisebackend.users.excpetions.UserNotFoundException;
import colepp.app.wealthwisebackend.users.mappers.UserMapper;
import colepp.app.wealthwisebackend.users.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        var user = userService.getUser(id);
        return ResponseEntity.ok().body(userMapper.userToUserDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserRequest request) {
        var user = userService.updateUser(id,request);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserLoginResponse> registerUser(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder) {

        var user = userService.registerUser(request);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        var token = userService.registerSignIn(user.getEmail());
        System.out.println(token);
        return ResponseEntity.created(uri).body(token);
    }

    @PostMapping("/email_test")
    public ResponseEntity<Void> testEmailService(){
        userService.sendTestEmail();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register_email")
    public ResponseEntity<Void> sendRegisterEmail(@RequestHeader("Authorization") String token) {
        System.out.println("Registering Email");
        userService.sendRegistrationEmail(token);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

//    ERROR HANDLERS
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("User Not Found", LocalDateTime.now()));

    }

    @ExceptionHandler(AccountExistException.class)
    public ResponseEntity<ErrorDto> handleAccountExistException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto("User already exists", LocalDateTime.now()));
    }




}
