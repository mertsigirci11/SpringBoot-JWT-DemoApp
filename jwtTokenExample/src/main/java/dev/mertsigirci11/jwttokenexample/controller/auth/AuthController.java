package dev.mertsigirci11.jwttokenexample.controller.auth;

import dev.mertsigirci11.jwttokenexample.entity.dto.AuthenticationRequestDto;
import dev.mertsigirci11.jwttokenexample.entity.dto.AuthenticationResponseDto;
import dev.mertsigirci11.jwttokenexample.entity.dto.RegisterRequestDto;
import dev.mertsigirci11.jwttokenexample.service.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto)
    {
        return ResponseEntity.ok(authenticationService.register(registerRequestDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto authenticationRequestDto)
    {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDto));
    }
}
