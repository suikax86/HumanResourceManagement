package da.hms.employeeservice.model.dto;

import lombok.Data;

@Data
public class EmployeeDto {

    private String name;
    private String email;
    private String idNumber;
    private String taxNumber;
    private String address;
    private String phoneNumber;
    private String bankName;
    private String bankNumber;
    private int rewardPoints;

    public EmployeeDto(String name, String email, String idNumber, String taxNumber, String address, String phoneNumber, String bankName, String bankNumber, int rewardPoints) {
        this.name = name;
        this.email = email;
        this.idNumber = idNumber;
        this.taxNumber = taxNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.bankName = bankName;
        this.bankNumber = bankNumber;
        this.rewardPoints = rewardPoints;
    }
}
