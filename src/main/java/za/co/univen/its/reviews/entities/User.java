package za.co.univen.its.reviews.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String  username;
    private String password;
    private String roles;
    private String surname;
    private String firstname;

    private String errorMessage;





}
