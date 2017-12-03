package pl.edu.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.ug.model.Album;
import pl.edu.ug.model.User;
import pl.edu.ug.service.AlbumService;
import pl.edu.ug.service.UserService;
import pl.edu.ug.validator.AlbumValidator;

import java.util.List;

@Controller
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private UserService userService;

    @Autowired
    private AlbumValidator albumValidator;

    //Create Album
    @RequestMapping(value = "/createAlbum", method = RequestMethod.GET)
    public String createAlbum(Model model){
        model.addAttribute("albumForm", new Album());
        return "Album/create";
    }

    @RequestMapping(value = "/createAlbum", method = RequestMethod.POST)
    public String createAlbum(@ModelAttribute("albumForm") Album albumForm, BindingResult bindingResult){
        albumValidator.validate(albumForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "Album/create";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            if(auth.isAuthenticated()) {
                albumForm.setAuthor(userService.findByUsername(auth.getName()));
                if(albumForm.getAuthor() == null) return "Album/create";
            }
        }

        albumService.add(albumForm);

        return "redirect:/" + albumForm.getAuthor().getUsername() + "/" + albumForm.getId();
    }

    //Read Album
    //TODO: Dokończyć - lista zdjęć, komentarze, ocena
    @RequestMapping(value = "/{username}/{albumID}", method = RequestMethod.GET)
    public String viewAlbum(@PathVariable String username, @PathVariable Long albumID, Model model){
        Album album = albumService.get(albumID);
        //User user = userService.findByUsername(username);
        if(album != null) {
            /*
            double global = scoreService.getGlobalScore(album);
            model.addAttribute("globalScore", global);
            */
            model.addAttribute("album", album);
            return "Album/view";
        }
        return "Album/notFound";
    }

    @RequestMapping(value = "/{username}/albums", method = RequestMethod.GET)
    public String listAll(@PathVariable String username, Model model){
        User user = userService.findByUsername(username);
        if(user != null) {
            List<Album> albums = albumService.getAll(user);
            model.addAttribute("albums", albums);
            return "Album/list";
        }
        return "Album/notFound";
    }

    //Update Album
    @RequestMapping(value = "/{username}/{albumID}/edit", method = RequestMethod.GET)
    public String editAlbum(@PathVariable String username, @PathVariable Long albumID, Model model){
        User user = userService.findByUsername(username);
        Album album = albumService.get(albumID);
        if(user != null && album != null){
            model.addAttribute("albumForm", album);
            return "Album/edit";
        }
        else return "Album/notFound";
    }

    @RequestMapping(value = "/{username}/{albumID}/edit", method = RequestMethod.POST)
    public String editAlbum(@PathVariable String username, @PathVariable Long albumID,
                            @ModelAttribute("albumForm") Album albumForm, BindingResult bindingResult){
        albumValidator.validate(albumForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "redirect:/" + username + "/" + albumID + "/edit";
        }

        albumForm.setAuthor(userService.findByUsername(albumForm.getAuthor().getUsername()));

        albumService.add(albumForm);

        return "redirect:/" + albumForm.getAuthor().getUsername() + "/" + albumForm.getId();
    }

    //Delete Album
    @RequestMapping(value = "{username}/{albumID}/delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    public String deleteAlbum(@PathVariable String username, @PathVariable Long albumID){
        User user = userService.findByUsername(username);
        if(user != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null) {
                if(auth.isAuthenticated()) {
                    if(auth.getName().equals(username)) {
                        Album album = albumService.get(albumID);
                        if (album != null) {
                            albumService.delete(album);
                            return "redirect:/" + username + "/albums";
                        }
                    }
                }
            }
        }
        return "Album/notFound";
    }


}
