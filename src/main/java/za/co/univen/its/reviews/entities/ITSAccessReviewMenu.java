package za.co.univen.its.reviews.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ITSACCESSREVIEWMENU")
public class ITSAccessReviewMenu {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String personNumber;
    private String personName;
    private String appointmentType;
    private String  username;
    private String functionCode;
    private String systemCode;
    private String menuName;
    private String menuOption;
    private String menuDesc;
    private String accessLevel;
    private boolean notUsed;
    @ManyToOne
    @JoinColumn(name = "menuoption_id")
    private ITSAccessReviewer accessReview;



}
