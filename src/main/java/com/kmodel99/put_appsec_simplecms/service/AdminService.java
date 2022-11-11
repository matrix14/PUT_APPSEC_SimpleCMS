package com.kmodel99.put_appsec_simplecms.service;

import com.kmodel99.put_appsec_simplecms.models.Article;
import com.kmodel99.put_appsec_simplecms.models.User;
import com.kmodel99.put_appsec_simplecms.repository.ArticleRepository;
import com.kmodel99.put_appsec_simplecms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class AdminService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity addNewArticle(HttpServletRequest r, String title, String content) {
        if(title.length()<20||title.length()>200) {
            return new ResponseEntity("Title should be between 20 and 200 characters!", HttpStatus.CONFLICT);
        }
        if(content.length()<20||content.length()>65535) {
            return new ResponseEntity("Content should be between 20 and 65535 characters!", HttpStatus.CONFLICT);
        }
        User u = userRepository.findByUsername(r.getUserPrincipal().getName());
        Article a = new Article(title, content, u, r.getRemoteAddr(), new Date(System.currentTimeMillis()));
        articleRepository.save(a);
        return new ResponseEntity("", HttpStatus.OK);
    }
}
