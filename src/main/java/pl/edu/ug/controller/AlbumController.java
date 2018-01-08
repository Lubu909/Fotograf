package pl.edu.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.ug.model.*;
import pl.edu.ug.service.AlbumService;
import pl.edu.ug.service.ScoreService;
import pl.edu.ug.service.UserService;
import pl.edu.ug.validator.AlbumValidator;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;

@Controller
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private AlbumValidator albumValidator;

    @Autowired
    private MessageSource messageSource;

    private boolean checkPath(String username, Long albumID){
        User user = userService.findByUsername(username);
        Album album = albumService.get(albumID);
        return album.getAuthor().getId().equals(user.getId());
    }

    private boolean isAuthor(Long albumID){
        Album album = albumService.get(albumID);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        return album.getAuthor().getId().equals(user.getId());
    }

    //Create Album
    @RequestMapping(value = "/createAlbum", method = RequestMethod.GET)
    public String createAlbum(Model model){
        model.addAttribute("albumForm", new Album());
        return "Album/create";
    }

    @RequestMapping(value = "/createAlbum", method = RequestMethod.POST)
    public String createAlbum(@ModelAttribute("albumForm") Album albumForm, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){
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

        String successMsg = messageSource.getMessage("messages.album.create.success",null, LocaleContextHolder.getLocale());
        redirectAttributes.addAttribute("username", albumForm.getAuthor().getUsername()).addAttribute("albumID", albumForm.getId());
        redirectAttributes.addFlashAttribute("success", successMsg);
        return "redirect:/{username}/{albumID}";
    }

    //Read Album
    @RequestMapping(value = "/{username}/{albumID}", method = RequestMethod.GET)
    public String viewAlbum(@PathVariable String username, @PathVariable Long albumID, Model model,
                            RedirectAttributes redirectAttributes){
        if(checkPath(username, albumID)) {
            Album album = albumService.get(albumID);
            Score emptyScore = new Score();
            emptyScore.setValue(0d);
            if (album.getId() != null) {
                double global = scoreService.getGlobalScore(album);
                model.addAttribute("globalScore", global);

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null) {
                    if (auth.isAuthenticated()) {
                        User user = userService.findByUsername(auth.getName());
                        if (user != null) {
                            Score userScore = scoreService.getUserScore(album, user);
                            try {
                                if (userScore.getValue() != null) {
                                    model.addAttribute("userScore", userScore.getValue());
                                    model.addAttribute("scoreForm", userScore);
                                } else model.addAttribute("scoreForm", emptyScore);
                            } catch (Exception e) {
                                model.addAttribute("scoreForm", emptyScore);
                            }
                        }
                    }
                } else model.addAttribute("scoreForm", emptyScore);

                album.getComments().sort(Comparator.comparing(Comment::getCreated).reversed());
                album.getPictures().sort(Comparator.comparing(Picture::getCreated));

                if (!model.containsAttribute("commentForm")) model.addAttribute("commentForm", new Comment());
                model.addAttribute("album", album);
                return "Album/view";
            }
            String errorMsg = messageSource.getMessage("messages.album.notFound", null, LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/";
        }
        throw new EntityNotFoundException("Wrong path");
    }

    @RequestMapping(value = "/{username}/albums", method = RequestMethod.GET)
    public String listAll(@PathVariable String username, Model model,
                          RedirectAttributes redirectAttributes){
        User user = userService.findByUsername(username);
        if(user.getId() != null) {
            List<Album> albums = albumService.getAll(user);
            model.addAttribute("albums", albums);
            return "Album/list";
        }
        String errorMsg = messageSource.getMessage("messages.album.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }

    //Update Album
    @RequestMapping(value = "/{username}/{albumID}/edit", method = RequestMethod.GET)
    public String editAlbum(@PathVariable String username, @PathVariable Long albumID, Model model,
                            RedirectAttributes redirectAttributes){
        if(checkPath(username, albumID)) {
            User user = userService.findByUsername(username);
            Album album = albumService.get(albumID);
            if(isAuthor(albumID)) {
                if (user.getId() != null && album.getId() != null) {
                    model.addAttribute("albumForm", album);
                    return "Album/edit";
                }
            }
            return "redirect:/denied";
        }
        throw new EntityNotFoundException("Wrong path");
    }

    @RequestMapping(value = "/{username}/{albumID}/edit", method = RequestMethod.POST)
    public String editAlbum(@PathVariable String username, @PathVariable Long albumID,
                            @ModelAttribute("albumForm") Album albumForm, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){
        if(checkPath(username, albumID)) {
            if(isAuthor(albumID)) {
                albumValidator.validate(albumForm, bindingResult);

                if (bindingResult.hasErrors()) {
                    redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
                    return "redirect:/{username}/{albumID}/edit";
                }

                albumForm.setAuthor(userService.findByUsername(albumForm.getAuthor().getUsername()));

                albumService.add(albumForm);

                String successMsg = messageSource.getMessage("messages.album.edit.success", null, LocaleContextHolder.getLocale());
                redirectAttributes.addAttribute("username", albumForm.getAuthor().getUsername()).addAttribute("albumID", albumForm.getId());
                redirectAttributes.addFlashAttribute("success", successMsg);
                return "redirect:/{username}/{albumID}";
            }
            return "redirect:/denied";
        }
        throw new EntityNotFoundException("Wrong path");
    }

    //Delete Album
    @RequestMapping(value = "{username}/{albumID}/delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    public String deleteAlbum(@PathVariable String username, @PathVariable Long albumID,
                              RedirectAttributes redirectAttributes){
        if(checkPath(username, albumID)) {
            if(isAuthor(albumID)) {
                Album album = albumService.get(albumID);
                if (album != null) {
                    albumService.delete(album);

                    String successMsg = messageSource.getMessage("messages.album.delete.success", null, LocaleContextHolder.getLocale());
                    redirectAttributes.addAttribute("username", username);
                    redirectAttributes.addFlashAttribute("success", successMsg);
                    return "redirect:/{username}/albums";
                }
            }
            return "redirect:/denied";
        }
        throw new EntityNotFoundException("Wrong path");
    }
}
