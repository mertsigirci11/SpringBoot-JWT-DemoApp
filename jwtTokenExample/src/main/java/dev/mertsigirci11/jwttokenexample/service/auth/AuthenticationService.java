package dev.mertsigirci11.jwttokenexample.service.auth;

import dev.mertsigirci11.jwttokenexample.entity.Role;
import dev.mertsigirci11.jwttokenexample.entity.User;
import dev.mertsigirci11.jwttokenexample.entity.dto.AuthenticationRequestDto;
import dev.mertsigirci11.jwttokenexample.entity.dto.AuthenticationResponseDto;
import dev.mertsigirci11.jwttokenexample.entity.dto.RegisterRequestDto;
import dev.mertsigirci11.jwttokenexample.repository.UserRepository;
import dev.mertsigirci11.jwttokenexample.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponseDto register(RegisterRequestDto registerRequestDto) {
        User user = User.builder()
                .firstName(registerRequestDto.getFirstName())
                .lastName(registerRequestDto.getLastName())
                .userEmail(registerRequestDto.getEmail())
                .userPassword(passwordEncoder.encode(registerRequestDto.getPassword()))
                .role(Role.USER)
                .build();
        /*User user = new User();
        user.setUserEmail(registerRequestDto.getFirstName());
        user.setLastName(registerRequestDto.getLastName());
        user.setUserEmail(registerRequestDto.getEmail());
        user.setUserPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setRole(Role.USER);*/


        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDto.getEmail(),
                        authenticationRequestDto.getPassword()
                )
        );

        User user = userRepository.findUserByUserEmail(authenticationRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwt = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwt)
                .build();
    }
}
