package za.co.univen.its.reviews.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column (name="personnel_number")
    private String personNumber;
    @Column (name="surname")
    private String surname;
    @Column (name="first_name")
    private String firstname;
    @Column (name="init")
    private String initials;
    @Column (name="id_number")
    private String idNo;
    @Column (name="post_name")
    private String postName;
    @Column (name="department_name")
    private String departmentName;
    @Column (name="appointment_date")
    private String appointmentDate;
    @Column (name="resignation_date")
    private String resignationdate;
    @Column (name="birth_date")
    private String dob;
    @Column (name="gnd")
    private String gender;
    @Column (name="direct_senior")
    private String supervisor;




}
