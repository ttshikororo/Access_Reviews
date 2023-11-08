package za.co.univen.its.reviews.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="ITSACCESSREVIEWSTATUSDETAILS")
public class ITSAccessReviewStatusDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private ITSAccessReviewStatus status;
    private String label;
}
