package za.co.univen.its.reviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.univen.its.reviews.entities.ITSAccessReviewMenu;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITSAccessReviewMenuRepo extends JpaRepository<ITSAccessReviewMenu,Long> {
    List<ITSAccessReviewMenu> findByPersonNumber(String staffNo);

    Optional<ITSAccessReviewMenu> findByPersonNumberAndMenuNameAndMenuOption(String personNumber, String menuName,String option);

    //@Query("SELECT c FROM ITSAccessReviewMenu c WHERE c.notUsed= ?1 AND c.personNumber =?2")
    List<ITSAccessReviewMenu> findByPersonNumberOrNotUsed(String staffNo,boolean used);

    List<ITSAccessReviewMenu> findByNotUsed(boolean used);


    ITSAccessReviewMenu findByIdIn(List<ITSAccessReviewMenu> menus);



}
