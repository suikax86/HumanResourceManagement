package da.hms.employeeservice.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployeeDto {
    @Nullable
    private String name;
    @Nullable
    @Email
    private String email;

    @Nullable
    @Pattern(regexp = "^[0-9]*$")
    @Size(min = 9, max = 12)
    private String idNumber;
    @Nullable
    @Pattern(regexp = "^[0-9]*$")
    @Size(min = 10, max = 13)
    private String taxNumber;
    @Nullable
    private String address;
    @Nullable
    @Pattern(regexp = "^(\\+84|0)[0-9]{9}$")
    private String phoneNumber;
    @Nullable
    private String bankName;
    @Nullable
    private String bankNumber;

    public UpdateEmployeeDto(String name, String email, String idNumber, String taxNumber, String address, String phoneNumber, String bankName, String bankNumber) {
        this.name = name;
        this.email = email;
        this.idNumber = idNumber;
        this.taxNumber = taxNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.bankName = bankName;
        this.bankNumber = bankNumber;
    }
}
