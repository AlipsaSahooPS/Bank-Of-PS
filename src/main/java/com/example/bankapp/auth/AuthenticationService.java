package com.example.bankapp.auth;

import com.example.bankapp.model.Customer;
import com.example.bankapp.model.Role;
import com.example.bankapp.repository.CustomerRepo;
import com.example.bankapp.security.JwtService;
import com.example.bankapp.token.Token;
import com.example.bankapp.token.TokenRepo;
import com.example.bankapp.token.TokenType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepo customerRepo;
    private final TokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse signUp(SignupRequest request) {
        Customer customer = Customer.builder()
                .customerId(request.getCustomerAadhar())
                .customerName(request.getCustomerName())
                .customerAddress(request.getCustomerAddress())
                .customerGender(request.getCustomerGender())
                .customerAadhar(request.getCustomerAadhar())
                .customerPhone(request.getCustomerPhone())
                .customerEmail(request.getCustomerEmail())
                .customerPassword(passwordEncoder.encode(request.getCustomerPassword()))
                .role(Role.CUSTOMER)
                .build();
        Customer savedCustomer = customerRepo.save(customer);
        String jwtToken = jwtService.generateToken(customer);
        saveCustomerToken(savedCustomer, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse logIn(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCustomerId(), request.getCustomerPassword()
                )
        );
        Customer customer = customerRepo.findById(request.getCustomerId()).orElseThrow();
        String jwtToken = jwtService.generateToken(customer);
        revokeAllCustomerTokens(customer);
        saveCustomerToken(customer,jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public String getTokenFromCustomerId(String customerId) {
        Token token = tokenRepo.getTokenFromTokenTable(customerId);
        return token.getToken();
    }
    // If we hit login request multiple times then multiple tokes are generated
    // to revoke all tokens except one we mark true expired and revoked column
    private void revokeAllCustomerTokens(Customer customer){
        List<Token> validUserTokens = tokenRepo.findAllValidTokensByCustomer(customer.getCustomerId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

    public void logOut(String customerId,String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        String jwt = authHeader.substring(7);
        Token storedToken = tokenRepo.findByToken(jwt).orElse(null);
        if (storedToken != null) {
            List<Token> tokenList= tokenRepo.deleteAllTokensByCustomerId(customerId);
            tokenRepo.deleteAll(tokenList);
            SecurityContextHolder.clearContext();
        }
    }
    private void saveCustomerToken(Customer savedCustomer, String jwtToken) {
        var token = Token.builder()
                .customer(savedCustomer)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepo.save(token);
    }
}
