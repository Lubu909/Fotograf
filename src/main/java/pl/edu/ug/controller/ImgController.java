package pl.edu.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.ug.model.User;
import pl.edu.ug.service.UserService;

import java.io.IOException;


@Controller
public class ImgController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/addImg", method = RequestMethod.GET)
    public String addImage(Model model) throws IOException {
        model.addAttribute("user", new User());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            if(auth.isAuthenticated()){
                User user = userService.findByUsername(auth.getName());
                if(user.getPhoto() != null && user.getPhoto().length > 0){
                    byte[] encodeBase64 = Base64.encode(user.getPhoto());
                    String base64Encoded = new String(encodeBase64, "UTF-8");
                    model.addAttribute("userImage", base64Encoded);
                }
            }
        }

        return "addImg";
    }

    @RequestMapping(value = "/addImg", method = RequestMethod.POST)
    public String addImage(@ModelAttribute("user") User model) throws IOException {



        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            if (auth.isAuthenticated()) {
                User user = userService.findByUsername(auth.getName());
                if (!model.getPhotoFile().isEmpty() || model.getPhotoFile() != null) {
                    user.setPhoto(model.getPhotoFile().getBytes());
                    userService.save(user);
                    return "redirect:/"+user.getUsername()+"/profile";
                }
            }
        }
        return "addImg";
    }

    @RequestMapping(value = "/{username}/avatar", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getAvatar(@PathVariable String username){
        User user = userService.findByUsername(username);
        if(user != null){
                if(user.getPhoto() != null && user.getPhoto().length > 0){
                    return user.getPhoto();
                }
            }
        return null;
    }

}
