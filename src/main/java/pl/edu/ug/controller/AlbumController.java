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
        /*
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            if(auth.isAuthenticated()) {
                Album album = new Album();
                album.setAuthor(userService.findByUsername(auth.getName()));
                if(album.getAuthor() != null) {
                    model.addAttribute("albumForm", album);
                    return "Album/createAlbum";
                }
            }
        }
        return "redirect:/welcome";
        */
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

        return "redirect:/" + albumForm.getAuthor().getUsername() + "/" + albumForm.getName();
    }

    //Read Album
    //TODO: Dokończyć - lista zdjęć, komentarze, ocena
    @RequestMapping(value = "/{username}/{albumName}", method = RequestMethod.GET)
    public String viewAlbum(@PathVariable String username, @PathVariable String albumName, Model model){
        /*
        User user = userService.findByUsername(username);
        Album album = albumService.findByName(albumName);
        if(user != null && album != null) return "Album/viewAlbum";
        else return "Album/notFound";
        */
        User user = userService.findByUsername(username);
        if(user != null) {
            Album album = albumService.findByName(albumName, user);
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
    @RequestMapping(value = "/{username}/{albumName}/edit", method = RequestMethod.GET)
    public String editAlbum(@PathVariable String username, @PathVariable String albumName, Model model){
        User user = userService.findByUsername(username);
        Album album = albumService.findByName(albumName);
        if(user != null && album != null){
            model.addAttribute("albumForm", album);
            return "Album/editAlbum";
        }
        else return "Album/notFound";
    }

    @RequestMapping(value = "/{username}/{albumName}/edit",
            method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH})
    public String editAlbum(@PathVariable String username, @PathVariable String albumName,
                            @ModelAttribute("albumForm") Album albumForm, BindingResult bindingResult){
        albumValidator.validate(albumForm, bindingResult);

        //System.out.println("Sprawdza");
        //if(albumForm.getAuthor() != null) System.out.println("Autor: " + albumForm.getAuthor().getUsername());

        if (bindingResult.hasErrors()) {
            return "redirect:/" + username + "/" + albumName + "/edit";
        }

        //System.out.println("Zapisuje");

        albumService.add(albumForm);

        return "redirect:/" + username + "/" + albumName;
    }

    //Delete Album
    /*
    @RequestMapping(value = "{username}/{albumName}/delete", method = RequestMethod.GET)
    public String deleteAlbum(@PathVariable String username, @PathVariable String albumName, Model model){
        User user = userService.findByUsername(username);
        if(user != null) {
            Album album = albumService.findByName(albumName, user);
            if(album != null){
                model.addAttribute("album", album);
                return "Album/deleteAlbum";
            }
        }
        //Album album = albumService.findByName(albumName);

        //model.addAttribute("albumForm", album);
        //User user = userService.findByUsername(username);
        //if(user != null && album != null) return "deleteAlbum";
        return "Album/notFound";
    }
    */

    //TODO: Dokończyć
    @RequestMapping(value = "{username}/{albumName}/delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    public String deleteAlbum(@PathVariable String username, @PathVariable String albumName){
        System.out.println("Wchodzi w usuwanie");
        User user = userService.findByUsername(username);
        if(user != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null) {
                if(auth.isAuthenticated()) {
                    System.out.println("Sprawdzanie username");
                    if(auth.getName().equals(username)) {
                        Album album = albumService.findByName(albumName, user);
                        if (album != null) {
                            System.out.println("Usuwam");
                            albumService.delete(album);
                            return "redirect:/" + username + "/albums";
                        }
                    }
                }
            }
        }
        /*
        if(user != null && album != null){
            albumService.delete(album);
            return "redirect:/welcome";
        }
        */
        return "Album/notFound";
    }


}
