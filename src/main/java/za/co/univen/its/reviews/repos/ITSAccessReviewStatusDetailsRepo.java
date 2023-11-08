package za.co.univen.its.reviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.univen.its.reviews.entities.ITSAccessReviewStatusDetails;
@Repository
public interface ITSAccessReviewStatusDetailsRepo extends JpaRepository<ITSAccessReviewStatusDetails,Long> {
}
