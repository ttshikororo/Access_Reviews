package za.co.univen.its.reviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.univen.its.reviews.entities.ITSAccessReviewStatus;
import za.co.univen.its.reviews.entities.ITSAccessReviewer;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITSAccessReviewerRepo extends JpaRepository <ITSAccessReviewer,Long> {

    Optional<ITSAccessReviewer> findByPersonNumber(String personNumber);
    List<ITSAccessReviewer> findBySupervisorAndStatus(String supervisor, ITSAccessReviewStatus submitted);

    Optional<ITSAccessReviewer> findBySupervisor(String personNumber);
}
