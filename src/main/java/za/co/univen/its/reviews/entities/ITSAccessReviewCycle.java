package za.co.univen.its.reviews.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Data
@Table(name = "ITSACCESSREVIEWCYCLE")
public class ITSAccessReviewCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date cycle;
}
