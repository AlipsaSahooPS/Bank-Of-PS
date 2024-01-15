package com.example.bankapp.auth;

import com.example.bankapp.model.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String customerId;
    private String customerName, customerAddress, customerGender;
    @NotEmpty @Pattern(regexp = "^[0-9]{12}$", message = "Aadhar number must be exactly 12 digits")
    private String customerAadhar;
    @NotEmpty @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String customerPhone;
    @NotEmpty @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Email is not valid")
    private String customerEmail;
    @NotEmpty @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&]{6,}$", message = "Password is not valid")
    private String customerPassword;
    private float customerBalance;
}
