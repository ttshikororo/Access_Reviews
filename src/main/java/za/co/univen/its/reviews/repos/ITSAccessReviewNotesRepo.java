package za.co.univen.its.reviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.univen.its.reviews.entities.ITSAccessReviewNotes;

import java.util.List;

@Repository
public interface ITSAccessReviewNotesRepo extends JpaRepository<ITSAccessReviewNotes,Long> {
    List<ITSAccessReviewNotes> findByPersonNumber(String staffNumber);
}
