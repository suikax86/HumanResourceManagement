package da.hms.employeeservice.model.dto;

import da.hms.employeeservice.model.enums.FormStatus;
import da.hms.employeeservice.model.enums.FormType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FormDto {
    private Long id;
    private Long employeeId;
    private Long approverId;
    private String name;
    private String approverName;
    private String phone;
    private LocalDate startDate;
    private LocalDate endDate;
    private FormStatus formStatus;
    private FormType formType;
    private String reason;
    private String comment;

    public FormDto(Long id, Long employeeId, Long approverId, String name, String approverName, String phone, LocalDate startDate, LocalDate endDate, FormStatus formStatus, FormType formType, String reason, String comment) {
        this.id = id;
        this.employeeId = employeeId;
        this.approverId = approverId;
        this.name = name;
        this.approverName = approverName;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.formStatus = formStatus;
        this.formType = formType;
        this.reason = reason;
        this.comment = comment;
    }
}
