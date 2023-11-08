package za.co.univen.its.reviews.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CommunicationDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String personNumber;
    private String contactType;
    private String communicationType;
    private String communicationNumber;
}
