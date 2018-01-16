package pl.edu.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.ug.model.User;
import pl.edu.ug.service.UserService;
import org.springframework.validation.BindingResult;
import pl.edu.ug.validator.UserManageValidator;

import java.util.List;

@Controller
public class UsersManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManageValidator userManageValidator;

    @RequestMapping(value = "/usersList", method = RequestMethod.GET)
    public String getUsersList(Model model) {
        model.addAttribute("user", new User());
        List<User> usersList = userService.getUsers();
        model.addAttribute("usersList", usersList);
        return "usersList";
    }

    @RequestMapping(value = "/usersList", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userManageValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "usersList";
        }
        User updated = userService.findByUsername(user.getUsername());
        updated.setSurname(user.getSurname());
        updated.setName(user.getName());
        updated.setEmail(user.getEmail());
        updated.setCity(user.getCity());
        updated.setTel(user.getTel());

        userService.save(updated);

        return "redirect:/usersList";
    }

    @RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/usersList";
    }

    @RequestMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getOne(id);
        model.addAttribute("user", user);
        model.addAttribute("usersList", userService.getUsers());
        return "usersList";
    }

}
