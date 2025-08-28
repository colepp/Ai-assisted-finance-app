package colepp.app.wealthwisebackend.users.repositories;

import colepp.app.wealthwisebackend.users.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u left join fetch u.userBanking where u.id = :userId")
    Optional<User> findUserWithUserBankingInfo(@Param("userId") Long userId);

    boolean existsByEmail(String email);
}
