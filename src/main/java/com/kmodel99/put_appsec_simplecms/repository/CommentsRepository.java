package com.kmodel99.put_appsec_simplecms.repository;

import com.kmodel99.put_appsec_simplecms.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByArticle_Id(Long article_id);
}
