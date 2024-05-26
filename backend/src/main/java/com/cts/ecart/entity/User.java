package com.cts.ecart.entity;

import com.cts.ecart.constant.ProfessionType;
import com.cts.ecart.constant.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, name = "username")
    private String username;

    @Email
    @Column(unique = true, nullable = false, name = "email")
    private String email;

    @NotBlank(message = "Password can not be empty")
    @Column(name = "password", nullable = false)
    private String password;

    @Past(message = "Birthday must be in past")
    @Column(name="birthday", nullable = false)
    private Date birthday;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Profession can not be empty")
    @Column(name = "profession", nullable = false)
    private ProfessionType profession;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "varchar(100) default 'USER'")
    private RoleType role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @OneToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

}
