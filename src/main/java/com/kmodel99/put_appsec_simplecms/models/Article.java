package com.kmodel99.put_appsec_simplecms.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_article")
    @Getter
    @Setter
    private Long id;
    @Column(columnDefinition = "TINYTEXT")
    @Length(min = 20)
    @Getter
    @Setter
    private String title;
    @Column(columnDefinition = "TEXT")
    @Length(min = 20)
    @Getter
    @Setter
    private String articleText;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;
    @Getter
    @Setter
    private String ip;
    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date createdAt;

    public Article(String title, String articleText, User user, String ip, Date createdAt) {
        this.title = title;
        this.articleText = articleText;
        this.user = user;
        this.ip = ip;
        this.createdAt = createdAt;
    }
}
