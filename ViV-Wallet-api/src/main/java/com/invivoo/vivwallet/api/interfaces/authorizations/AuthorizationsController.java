package com.invivoo.vivwallet.api.interfaces.authorizations;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.invivoo.vivwallet.api.application.security.JWTTokenProvider;
import com.invivoo.vivwallet.api.domain.user.User;
import com.invivoo.vivwallet.api.domain.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(AuthorizationsController.API_V_1_AUTH)
public class AuthorizationsController {

    public static final String API_V_1_AUTH = "/api/Authorizations";

    private final JWTTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public AuthorizationsController(JWTTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authorize(HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        DecodedJWT verify = jwtTokenProvider.verify(token);
        String userName = verify.getClaim("user").asString();
        List<String> roles = userRepository.findByFullName(userName)
                                           .map(this::getRoles)
                                           .orElseGet(ArrayList::new);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                                                       .roles(roles)
                                                       .build());
    }

    private List<String> getRoles(User user) {
        return Collections.emptyList();
    }
}
