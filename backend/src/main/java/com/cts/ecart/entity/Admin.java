package com.cts.ecart.entity;

import com.cts.ecart.constant.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin")
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false, name = "username")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Email
    @Column(unique = true, nullable = false, name = "email")
    private String email;

    @Past(message = "Birthday must be in past")
    @Column(name="birthday", nullable = false)
    private Date birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "varchar(100) default 'ADMIN'")
    private RoleType role;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;


}
