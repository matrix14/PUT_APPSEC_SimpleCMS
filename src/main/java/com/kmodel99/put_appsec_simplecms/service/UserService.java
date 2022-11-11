package com.kmodel99.put_appsec_simplecms.service;

import com.kmodel99.put_appsec_simplecms.models.Article;
import com.kmodel99.put_appsec_simplecms.models.Comments;
import com.kmodel99.put_appsec_simplecms.models.Role;
import com.kmodel99.put_appsec_simplecms.models.User;
import com.kmodel99.put_appsec_simplecms.repository.ArticleRepository;
import com.kmodel99.put_appsec_simplecms.repository.CommentsRepository;
import com.kmodel99.put_appsec_simplecms.repository.UserRepository;
import com.kmodel99.put_appsec_simplecms.security.SecurityConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    private void printLog(String s) {
        logger.info(s);
    }

    public ResponseEntity register(String username, String pass1, String pass2, HttpServletRequest r) {
        if(username.length()<5||username.length()>20)
            return new ResponseEntity("Login must be between 5 and 20 characters long!", HttpStatus.CONFLICT);
        if(pass1.length()<5||pass1.length()>30)
            return new ResponseEntity("Password must be between 5 and 30 characters long!", HttpStatus.CONFLICT);
        if(!pass1.equals(pass2)) {
            return new ResponseEntity("Password doesn't match each other!", HttpStatus.CONFLICT);
        }
        if(userRepository.existsByUsername(username)) {
            return new ResponseEntity("Username already taken!", HttpStatus.CONFLICT);
        }
        User u = new User(username, BCrypt.hashpw(pass1, BCrypt.gensalt(12)), Arrays.asList(Role.ROLE_USER), true);
        userRepository.save(u);
        printLog("[REGISTER] User: "+u.getUsername()+" IP: "+r.getRemoteAddr());
        return new ResponseEntity("Account created! You can now login!", HttpStatus.OK);
    }

    public List<Article> getAllArticles() {
        List<Article> la = articleRepository.findByOrderByCreatedAtDesc();
        return la;
    }

    public Article getArticleById(Long id) {
        Article a = articleRepository.getArticleById(id);
        return a;
    }

    public List<Comments> getCommentsByArticleId(Long articleId) {
        return commentsRepository.findAllByArticle_Id(articleId);
    }

    public ResponseEntity addNewComment(HttpServletRequest r, String content, Long articleId) {
        if(content.length()<20||content.length()>1000) {
            return new ResponseEntity("Content should be between 20 and 1000 characters!", HttpStatus.CONFLICT);
        }
        User u = userRepository.findByUsername(r.getUserPrincipal().getName());
        Article a = articleRepository.getArticleById(articleId);
        Comments c = new Comments(content, u, a, r.getRemoteAddr(), new Date(System.currentTimeMillis()));
        commentsRepository.save(c);
        return new ResponseEntity("", HttpStatus.OK);
    }
}
