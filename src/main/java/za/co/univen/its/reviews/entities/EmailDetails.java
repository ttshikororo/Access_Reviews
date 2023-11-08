package za.co.univen.its.reviews.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
