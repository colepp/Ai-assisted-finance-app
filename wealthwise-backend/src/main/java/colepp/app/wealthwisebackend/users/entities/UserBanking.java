package colepp.app.wealthwisebackend.users.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_banking", schema = "wealthwisedb")
public class UserBanking {
    @Id
    @Column(name = "id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "access_token")
    private String accessToken;


    @Column(name = "account_link_status")
    @Enumerated(EnumType.STRING)
    private UserLinkStatus accountLinkStatus = UserLinkStatus.LINK_STATUS_NOT_CREATED;

}
