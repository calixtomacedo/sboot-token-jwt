package br.com.cmdev.token.jwt.controllers;

import br.com.cmdev.token.jwt.models.User;
import br.com.cmdev.token.jwt.models.dtos.RegisterRequest;
import br.com.cmdev.token.jwt.models.dtos.UserRequest;
import br.com.cmdev.token.jwt.models.dtos.UserResponse;
import br.com.cmdev.token.jwt.repositories.UserRepository;
import br.com.cmdev.token.jwt.security.TokenJwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/cmdev")
public class TokenJwtController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenJwtService tokenJwtService;

    public TokenJwtController(AuthenticationManager authenticationManager, UserRepository repository, TokenJwtService tokenJwtService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.tokenJwtService = tokenJwtService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody @Valid UserRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var tokenJwt = this.tokenJwtService.generateTokenJwt((User) auth.getPrincipal());
        return ResponseEntity.ok(new UserResponse(tokenJwt));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest request) {
        if (this.repository.findByEmail(request.email()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encrytedPassword = new BCryptPasswordEncoder().encode(request.password());
        var user = new User(request.name(), request.email(), encrytedPassword, request.role(), true, LocalDateTime.now(), null);
        this.repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
