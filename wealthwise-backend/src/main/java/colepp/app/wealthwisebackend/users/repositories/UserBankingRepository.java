package colepp.app.wealthwisebackend.users.repositories;

import colepp.app.wealthwisebackend.users.entities.UserBanking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBankingRepository extends JpaRepository<UserBanking,Long> {
}
