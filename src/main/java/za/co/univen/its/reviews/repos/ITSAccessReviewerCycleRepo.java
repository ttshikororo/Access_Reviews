package za.co.univen.its.reviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.univen.its.reviews.entities.ITSAccessReviewCycle;

@Repository
public interface ITSAccessReviewerCycleRepo extends JpaRepository<ITSAccessReviewCycle,Long> {
    ITSAccessReviewCycle findByCycle(ITSAccessReviewCycle cycle);
}
