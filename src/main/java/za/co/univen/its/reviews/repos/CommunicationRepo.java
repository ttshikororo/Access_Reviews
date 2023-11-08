package za.co.univen.its.reviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.univen.its.reviews.entities.CommunicationDetails;

import java.util.Optional;
@Repository
public interface CommunicationRepo extends JpaRepository<CommunicationDetails,Long> {
    Optional<CommunicationDetails> findByPersonNumber(String personNumber);
}
