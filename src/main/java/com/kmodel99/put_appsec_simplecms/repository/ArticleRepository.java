package com.kmodel99.put_appsec_simplecms.repository;

import com.kmodel99.put_appsec_simplecms.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findFirst10ByOrderByCreatedAtDesc();
    List<Article> findByOrderByCreatedAtDesc();
    Article getArticleById(Long id);
}
