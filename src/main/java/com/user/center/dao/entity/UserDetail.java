package com.user.center.dao.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "user_detail", schema = "user_center")
public class UserDetail extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    // CascadeType.REMOVE for archive
    @OneToOne(mappedBy = "userDetail", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Profile profile;

    public UserDetail() {
        super();
    }

    public UserDetail(String username, String password) {
        this.username = username;
        this.password = password;
    }


}