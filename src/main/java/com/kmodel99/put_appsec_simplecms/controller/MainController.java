package com.kmodel99.put_appsec_simplecms.controller;

import com.kmodel99.put_appsec_simplecms.service.AdminService;
import com.kmodel99.put_appsec_simplecms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.basic.BasicLookAndFeel;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public String indexPage2(Model model) {
        return indexPage(model);
    }

    @GetMapping("index")
    public String indexPage(Model model) {
        model.addAttribute("articles", userService.getAllArticles());
        return "index";
    }

    @GetMapping("article")
    public String articlePage(Model model, @RequestParam(value="id") String sId) {
        Long id = Long.parseLong(sId);
        model.addAttribute("article", userService.getArticleById(id));
        model.addAttribute("articleComments", userService.getCommentsByArticleId(id));
        return "article";
    }

    @GetMapping("login")
    public String loginPage(@RequestParam(value = "registerSuccesfull", required = false) String registerSuccesfull,
                            Model model) {
        model.addAttribute("registerSuccesfull", registerSuccesfull);
        return "login";
    }

    @GetMapping("register")
    public String registerPage(@RequestParam(value = "registerFail", required = false) String registerFail,
                               @RequestParam(value = "registerFailDesc", required = false) String registerFailDesc,
                               Model model) {
        model.addAttribute("registerFail", registerFail);
        model.addAttribute("registerFailDesc", registerFailDesc);
        return "register";
    }

    @PostMapping("signup")
    public String signup(HttpServletRequest r,
                                    @RequestParam String txtUsername,
                                    @RequestParam String txtPassword,
                                    @RequestParam String txtPassword2) {
        ResponseEntity resEnt = userService.register(txtUsername, txtPassword, txtPassword2, r);
        if(resEnt.getStatusCode()== HttpStatus.OK)
            return "redirect:/login?registerSuccesfull";
        else
            return "redirect:/register?registerFail&registerFailDesc="+resEnt.getBody();
        //return userService.register(txtUsername, txtPassword, txtPassword2, r);
    }
    @PostMapping("register")
    public String registerPost(HttpServletRequest r,
                                    @RequestParam String txtUsername,
                                    @RequestParam String txtPassword,
                                    @RequestParam String txtPassword2) {
        return signup(r, txtUsername, txtPassword, txtPassword2);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_WRITER')")
    @GetMapping("admin/index")
    public String getAdminIndex() {
        return "admin/index";
    }

    @GetMapping("admin")
    public String getAdminIndex2() {
        return getAdminIndex();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_WRITER')")
    @PostMapping("admin/addArticle")
    public String addArticle(HttpServletRequest r,
                             @RequestParam String txtTitle,
                             @RequestParam String txtArticleContent) {
        var ret = adminService.addNewArticle(r, txtTitle, txtArticleContent);
        if(ret.getStatusCode() == HttpStatus.CONFLICT)
            return "redirect:/admin/index?addArticleFailed&error="+ret.getBody();
        else
            return "redirect:/admin/index?addArticleSuccesfull&addArticleTitle="+txtTitle;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("addComment")
    public String addComment(Model model, HttpServletRequest r, @RequestParam String articleId, @RequestParam String txtCommentContent) {
        var re = userService.addNewComment(r, txtCommentContent, Long.parseLong(articleId));
        if(re.getStatusCode() == HttpStatus.OK)
            return "redirect:/article?id="+articleId+"&addCommentSuccesfull";
        else
            return "redirect:/article?id="+articleId+"&addCommentFail&error="+re.getBody();
        //return articlePage(model, articleId);
    }
}
