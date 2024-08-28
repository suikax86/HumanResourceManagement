package da.hms.employeeservice.model.dto;

import da.hms.employeeservice.model.enums.FormStatus;
import da.hms.employeeservice.model.enums.FormType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddFormDto {
    @NotNull
    private Long employeeId;
    private LocalDate startDate;
    @FutureOrPresent(message = "End date cannot be in the past")
    private LocalDate endDate;
    private FormType formType;
    private String reason;

    public AddFormDto() {
    }

    public AddFormDto(Long employeeId, LocalDate startDate, LocalDate endDate, FormType formType, String reason) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.formType = formType;
        this.reason = reason;
    }
}
