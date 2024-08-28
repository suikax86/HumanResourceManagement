package da.hms.employeeservice.model.dto;

import da.hms.employeeservice.model.enums.FormStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveFormDto {
    private Long approverId;
    private FormStatus formStatus;
    private String comment;

    public ApproveFormDto() {
    }

    public ApproveFormDto(Long approverId, FormStatus formStatus, String comment) {
        this.approverId = approverId;
        this.formStatus = formStatus;
        this.comment = comment;
    }
}
