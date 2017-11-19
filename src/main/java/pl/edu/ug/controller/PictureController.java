package pl.edu.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    private AlbumService albumService;

    @Autowired
    private UserService userService;

    //Create Picture
    @RequestMapping(value = "/{username}/{albumID}/add", method = RequestMethod.GET)
    public String addPhoto(@PathVariable String username, @PathVariable Long albumID, Model model){
        model.addAttribute("albumID", albumID);
        model.addAttribute("photoForm", new Picture());
        return "Album/Photo/addPhoto";
    }

    @RequestMapping(value = "/{username}/{albumID}/add", method = RequestMethod.POST)
    public String addPhoto(@PathVariable String username,@PathVariable Long albumID,
                           @ModelAttribute("photoForm") Picture photoForm, BindingResult bindingResult){
        //TODO: Validator

        //photoForm.setAlbum(albumService.get(albumID, userService.findByUsername(username)));
        photoForm.setAlbum(albumService.get(albumID));
        System.out.println("Uploadowanie pliku " + photoForm.getTitle() + ", o rozmiarze " + photoForm.getPhoto().length);
        pictureService.add(photoForm);

        return "redirect:/" + username + "/" + albumID;
    }

    //Read Picture
    public String getPhoto(){
        return "Album/Photo/view";
    }

    //Update Picture
    //Delete Picture
}
