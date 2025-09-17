package colepp.app.wealthwisebackend.users.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "users", schema = "wealthwisedb")
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

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "verification_status")
    @Enumerated(EnumType.STRING)
    private UserVerificationStatus verificationStatus = UserVerificationStatus.UNVERIFIED;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private UserBanking userBanking;

}
