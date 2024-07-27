package da.hms.employeeservice.model.client;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
@Getter
@Setter
public class RewardPointsTransaction {
    @Id
    private String id;
    private int senderId;
    private int receiverId;
    private int points;
    private TransactionType transactionType;
    private String description;
    private LocalDateTime timeStamp;

}