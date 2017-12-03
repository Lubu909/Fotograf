package pl.edu.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.ug.model.User;
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
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        //String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + /*operationSetExper*/ ":|~|>|<" + ")(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        //System.out.println("Pattern: " + pattern.toString());
        /*while (matcher.find()) {
            builder.with(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(4),
                    matcher.group(3),
                    matcher.group(5));
        }*/

        while (matcher.find()) {
            //System.out.println("Match:\n" + matcher.group(1) + "\n" + matcher.group(2) + "\n" + matcher.group(3));
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<User> userSpecification = builder.build();
        List<User> users = userService.search(userSpecification);
        model.addAttribute("users", users);
        System.out.println("Wyszukiwanie - znaleziono " + users.size() + " elementów");

        return "searchResults";
    }
}