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
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    @Getter
    @Setter
    private Long id;
    @Column(columnDefinition = "TEXT", length = 1000)
    @Length(min = 10)
    @Getter
    @Setter
    private String commentText;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;
    @ManyToOne
    @JoinColumn(name = "article_id")
    @Getter
    @Setter
    private Article article;
    @Getter
    @Setter
    private String ip;
    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date createdAt;

    public Comments(String commentText, User user, Article article, String ip, Date createdAt) {
        this.commentText = commentText;
        this.user = user;
        this.article = article;
        this.ip = ip;
        this.createdAt = createdAt;
    }
}
