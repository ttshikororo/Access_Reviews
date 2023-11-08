package za.co.univen.its.reviews.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ITSACCESSREVIEWER")
public class ITSAccessReviewer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column (name="personnel_number")
    private String personNumber;
    @Column (name="surname")
    private String surname;
    @Column (name="first_name")
    private String firstname;
    @Column (name="init")
    private String initials;
    @Column (name="post_name")
    private String postName;
    @Column (name="department_name")
    private String departmentName;

    @Column (name="direct_senior")
    private String supervisor;


    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewDate;

    @OneToMany(mappedBy="accessReview", fetch=FetchType.LAZY,orphanRemoval=true,cascade=CascadeType.ALL)
    private List<ITSAccessReviewNotes> Notes;
    @Enumerated(EnumType.STRING)
    private ITSAccessReviewStatus status;

}
