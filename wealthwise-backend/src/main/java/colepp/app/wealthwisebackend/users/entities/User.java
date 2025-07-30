package colepp.app.wealthwisebackend.users.entities;

import colepp.app.wealthwisebackend.finance.dtos.PlaidUserInfoDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "wealthwise_db")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;


    @Column(name = "access_token")
    private String accessToken;



    @Column(name = "verification_status")
    @Enumerated(EnumType.STRING)
    private UserVerificationStatus verificationStatus = UserVerificationStatus.UNVERIFIED;



    @Column(name = "account_link_status")
    @Enumerated(EnumType.STRING)
    private UserLinkStatus accountLinkStatus = UserLinkStatus.LINK_STATUS_NOT_CREATED;

    @Column(name = "phone_number")
    private String phoneNumber;

}