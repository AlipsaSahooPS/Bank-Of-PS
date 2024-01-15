package com.example.bankapp.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody SignupRequest request){
        try {
            return ResponseEntity.ok(authenticationService.signUp(request));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/logIn")
    public ResponseEntity<AuthenticationResponse> logIn(@RequestBody LoginRequest request){
        try {
            return ResponseEntity.ok(authenticationService.logIn(request));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/logOut/{customerId}")
    public void logOut(@PathVariable String customerId, HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        try {
            authenticationService.logOut(customerId,authHeader);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/getTokenFromCustomerId/{customerId}")
    public ResponseEntity<String> getTokenFromCustomerId(@PathVariable String customerId) {
        try {
            return ResponseEntity.ok(authenticationService.getTokenFromCustomerId(customerId));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
