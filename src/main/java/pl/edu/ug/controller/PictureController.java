package pl.edu.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.ug.model.Album;
import pl.edu.ug.model.Picture;
import pl.edu.ug.service.AlbumService;
import pl.edu.ug.service.PictureService;
import pl.edu.ug.service.UserService;

@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private UserService userService;

    //Create Picture
    @RequestMapping(value = "addPhoto", method = RequestMethod.GET)
    public String addPhoto(Model model){
        model.addAttribute("photoForm", new Album());
        return "Album/Photo/addPhoto";
    }

    @RequestMapping(value = "addPhoto", method = RequestMethod.POST)
    public String addPhoto(@ModelAttribute("photoForm") Picture photoForm, BindingResult bindingResult){
        //TODO: Validator

        pictureService.add(photoForm);

        return "redirect:/" + photoForm.getAlbum().getAuthor().getUsername() + "/" + photoForm.getAlbum().getName();
        //return "Album/viewAlbum";
    }

    //Read Picture
    //Update Picture
    //Delete Picture
}
