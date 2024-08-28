package da.hms.employeeservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    private String role;

    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^[0-9]*$")
    @Size(min = 9, max = 12)
    private String idNumber;

    @NotNull
    @Pattern(regexp = "^[0-9]*$")
    @Size(min = 10, max = 13)
    private String taxNumber;

    private String address;

    @NotNull
    @Pattern(regexp = "^(\\+84|0)[0-9]{9}$")
    private String phoneNumber;

    @NotNull
    private String bankName;

    @NotNull
    private String bankNumber;
}
