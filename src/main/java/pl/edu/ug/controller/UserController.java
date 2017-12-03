package pl.edu.ug.controller;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.ug.model.User;
import pl.edu.ug.search.RsqlVisitor;
import pl.edu.ug.search.SearchOperation;
import pl.edu.ug.search.UserSpecificationsBuilder;
import pl.edu.ug.service.SecurityService;
import pl.edu.ug.service.UserService;
import pl.edu.ug.validator.UserValidator;

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

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

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
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "query") String search, Model model){
        Node rootNode = new RSQLParser().parse(search);
        Specification<User> spec = rootNode.accept(new RsqlVisitor<User>());
        List<User> users = userService.search(spec);

        model.addAttribute("users", users);
        System.out.println("Wyszukiwanie - znaleziono " + users.size() + " elementów");

        return "searchResults";
    }

    @RequestMapping(value = "/searchJSON", method = RequestMethod.GET)
    @ResponseBody
    public List<User> searchJSON(@RequestParam(value = "query") String search){
        Node rootNode = new RSQLParser().parse(search);
        Specification<User> spec = rootNode.accept(new RsqlVisitor<User>());
        return userService.search(spec);
    }
}