package com.kmodel99.put_appsec_simplecms.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loginInfo")
public class LoginInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String ip;
    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    public LoginInfo(User user, String ip, Date createdAt) {
        this.user = user;
        this.ip = ip;
        this.createdAt = createdAt;
    }
}