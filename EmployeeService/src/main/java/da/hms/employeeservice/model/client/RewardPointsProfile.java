package da.hms.employeeservice.model.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RewardPointsProfile {
    @Id
    private String id;
    private int employeeId;
    private int totalPoints;
    private List<RewardPointsTransaction> pointsHistory = new ArrayList<>();


}
