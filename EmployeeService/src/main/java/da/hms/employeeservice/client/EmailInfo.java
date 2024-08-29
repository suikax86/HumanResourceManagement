package da.hms.employeeservice.client;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailInfo implements Serializable {
    private String toEmail;
    private String subject;
    private String message;

    public EmailInfo() {
    }

    public EmailInfo(String toEmail, String subject, String message) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.message = message;
    }
}
