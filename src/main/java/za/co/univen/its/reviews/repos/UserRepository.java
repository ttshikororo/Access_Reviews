package za.co.univen.its.reviews.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import za.co.univen.its.reviews.entities.User;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    User findUsersByUsername(String username);
    Optional<User> findOneByUsernameAndPassword(String username, String password);

    User findByUsernameAndPassword(String staffNumber, String password);
}
