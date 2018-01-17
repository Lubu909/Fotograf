package pl.edu.ug.controller;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.ug.java.EmailSender;
import pl.edu.ug.java.PasswordGenerator;
import pl.edu.ug.model.Album;
import pl.edu.ug.model.User;
import pl.edu.ug.search.RsqlVisitor;
import pl.edu.ug.search.SearchOperation;
import pl.edu.ug.search.UserSpecificationsBuilder;
import pl.edu.ug.service.AlbumService;
import pl.edu.ug.service.SecurityService;
import pl.edu.ug.service.UserService;
import pl.edu.ug.validator.UserValidator;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private PasswordGenerator passwordGenerator;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, @RequestParam("roleId") Integer roleId, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        emailSender.sendRegisterConformation(userForm.getEmail(), userForm.getUsername());

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        List<Album> albums = albumService.getBest(10);
        System.out.println("Top " + albums.size() + " albums");
        model.addAttribute("topAlbums", albums);
        return "welcome";
    }

    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String accessDenied(Model model, RedirectAttributes redirectAttributes) {
        String error = messageSource.getMessage("messages.accessDenied", null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", error);
        return "redirect:/";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }

    @RequestMapping(value = "/{username}/profile", method = RequestMethod.GET)
    public String userProfile(@PathVariable String username, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(username);
        if (user.getId() != null) {
            if (user.containsRole(User.ROLE_PHOTOGRAPHER)) model.addAttribute("isPhotographer", true);
            else model.addAttribute("isPhotographer", false);
            model.addAttribute("user", user);
            return "profile";
        }
        String errorMsg = messageSource.getMessage("messages.user.notFound", null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "query") String search, Model model) {
        Node rootNode = new RSQLParser().parse("name==*" + search.trim() + "*,city==*" + search.trim() + "*");
        Specification<User> spec = rootNode.accept(new RsqlVisitor<User>());
        List<User> users = userService.search(spec);

        if (!users.isEmpty()) users.sort(Comparator.nullsLast(Comparator.comparing(User::getCity)));

        List<User> photoUsers = new ArrayList<>();
        for (User user : users) {
            if (user.containsRole(User.ROLE_PHOTOGRAPHER)) photoUsers.add(user);
        }

        model.addAttribute("searchForm", new User());
        model.addAttribute("users", photoUsers);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.findByUsername(auth.getName());
            if (currentUser.containsRole(User.ROLE_ADMIN)) model.addAttribute("users", users);
        } catch (NullPointerException e) {
        }

        return "searchResults";
    }

    @RequestMapping(value = "/advSearch", method = RequestMethod.GET)
    public String advancedSearch(Model model) {
        if (!model.containsAttribute("searchForm"))
            model.addAttribute("searchForm", new User());
        return "searchResults";
    }

    @RequestMapping(value = "/advSearch", method = RequestMethod.POST)
    public String advancedSearch(@ModelAttribute("searchForm") User searchForm, Model model) {
        List<String> criteria = new ArrayList<>();
        searchForm.setName(searchForm.getName().trim());
        searchForm.setSurname(searchForm.getSurname().trim());
        searchForm.setUsername(searchForm.getUsername().trim());
        searchForm.setCity(searchForm.getCity().trim());
        if (!searchForm.getName().isEmpty())
            criteria.add("name==(\"*" + searchForm.getName().trim() + "*\")");
        if (!searchForm.getSurname().isEmpty())
            criteria.add("surname==(\"*" + searchForm.getSurname().trim() + "*\")");
        if (!searchForm.getUsername().isEmpty())
            criteria.add("username==(\"*" + searchForm.getUsername().trim() + "*\")");
        if (!searchForm.getCity().isEmpty())
            criteria.add("city==(\"*" + searchForm.getCity().trim() + "*\")");

        System.out.println(criteria);
        if (criteria.size() > 0) {
            //String query = String.join(",", criteria);        //OR
            String query = String.join(";", criteria);        //AND
            Node rootNode = new RSQLParser().parse(query);
            Specification<User> spec = rootNode.accept(new RsqlVisitor<User>());
            List<User> users = userService.search(spec);

            if (!users.isEmpty()) users.sort(Comparator.nullsLast(Comparator.comparing(User::getCity)));

            List<User> photoUsers = new ArrayList<>();
            for (User user : users) {
                if (user.containsRole(User.ROLE_PHOTOGRAPHER)) photoUsers.add(user);
            }

            model.addAttribute("users", photoUsers);

            try {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                User currentUser = userService.findByUsername(auth.getName());
                if (currentUser.containsRole(User.ROLE_ADMIN)) model.addAttribute("users", users);
            } catch (NullPointerException e) {
            }
        }
        model.addAttribute("searchForm", searchForm);
        return "searchResults";
    }

    /*
    @RequestMapping(value = "/searchJSON", method = RequestMethod.GET)
    public @ResponseBody List<User> searchJSON(@RequestParam(value = "query") String search){
        Node rootNode = new RSQLParser().parse("name==" + search + "*,city==" + search + "*");
        Specification<User> spec = rootNode.accept(new RsqlVisitor<User>());
        return userService.search(spec);
    }
    */

    @RequestMapping(value = "/forgotPasswd", method = RequestMethod.GET)
    public String forgotPasswd(Model model) {
        model.addAttribute("userName", new String());
        model.addAttribute("email", new String());
        return "forgotPasswd";
    }

    @RequestMapping(value = "/forgotPasswd", method = RequestMethod.POST)
    public String forgotPasswd(@ModelAttribute("username") String username, @ModelAttribute("email") String email) {
        String generatedPasswd = passwordGenerator.generatePasswd();
        User user = userService.findByUsername(username);
        if (user != null && username.equals(user.getUsername()) && email.equals(user.getEmail())) {
            user.setPassword(generatedPasswd);
            userService.save(user);
            emailSender.sendTemporaryPasswd(user.getEmail(), user.getUsername(), generatedPasswd);
            return "redirect:/forgotPasswdChange";
        }

        return "forgotPasswd";
    }

    @RequestMapping(value = "/forgotPasswdChange", method = RequestMethod.GET)
    public String forgotPasswdChange(Model model) {
        model.addAttribute("user", new User());
        return "forgotPasswdChange";
    }

    @RequestMapping(value = "/forgotPasswdChange", method = RequestMethod.POST)
    public String forgotPasswdChange(@ModelAttribute("user") User userForm, @ModelAttribute("genPasswd") String genPasswd) {

        User user = userService.findByUsername(userForm.getUsername());

        if (user != null /*&& user.getPassword().equals(genPasswd) && user.getUsername().equals(userForm.getUsername())*/) {
            user.setPassword(userForm.getPassword());
            userService.save(user);
            return "redirect:/login";
        }

        return "forgotPasswdChange";
    }

    @RequestMapping(value = "/{username}/changePasswd", method = RequestMethod.GET)
    public String changePasswd(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        if (user.getId() != null) {
            model.addAttribute("user", user);
            return "changePasswd";
        }

        return "redirect:/profile";
    }

    @RequestMapping(value = "{username}/changePasswd", method = RequestMethod.POST)
    public String changePasswd(@PathVariable String username, @ModelAttribute("userForm") User userForm) {

        User user = userService.findByUsername(username);
        if (user != null) {
            userService.save(user,userForm.getPassword());
            return "redirect:/{username}/profile";
        }

        return "redirect:/{username}/changePasswd";
    }

    @RequestMapping(value = "/{username}/editAccountDetails", method = RequestMethod.GET)
    public String editAccountDetails(@PathVariable String username, Model model){

        User user = userService.findByUsername(username);
        if (user.getId() != null) {
            model.addAttribute("user", user);
            return "editAccountDetails";
        }
        return "editAccountDetails";
    }

    @RequestMapping(value = "/{username}/editAccountDetails", method = RequestMethod.POST)
    public String editAccountDetails(@PathVariable String username, @ModelAttribute("userForm") User userForm){

        User user = userService.findByUsername(userForm.getUsername());
        if (user != null) {
            user.setName(userForm.getName());
            user.setSurname(userForm.getSurname());
            user.setCity(userForm.getCity());
            user.setEmail(userForm.getEmail());
            user.setTel(userForm.getTel());
            userService.save(user);
            return "redirect:/"+user.getUsername()+"/profile";
        }
        return "editAccountDetails";
    }

}