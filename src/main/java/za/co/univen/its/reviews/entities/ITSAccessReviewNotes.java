package za.co.univen.its.reviews.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="ITSACCESSREVIEWNOTES")
public class ITSAccessReviewNotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String personNumber;
    private String personNames;
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    private String message;
    @ManyToOne
    @JoinColumn(name = "menu_option_id")
    @JsonIgnore
    private ITSAccessReviewer accessReview;


}
