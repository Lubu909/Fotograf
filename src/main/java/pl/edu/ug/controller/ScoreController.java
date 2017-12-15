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
import pl.edu.ug.model.Album;
import pl.edu.ug.model.Score;
import pl.edu.ug.model.User;
import pl.edu.ug.service.AlbumService;
import pl.edu.ug.service.ScoreService;
import pl.edu.ug.service.UserService;
import pl.edu.ug.validator.ScoreValidator;

@Controller
public class ScoreController {

    @Autowired
    private UserService userService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ScoreValidator scoreValidator;

    @Autowired
    private MessageSource messageSource;

    //Create&Update score
    @RequestMapping(value = "/{username}/{albumID}/rateAlbum", method = RequestMethod.GET)
    public String createScore(@PathVariable String username, @PathVariable Long albumID, Model model){
        User user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            if (auth.isAuthenticated()) {
                user = userService.findByUsername(auth.getName());
                Score score = scoreService.getUserScore(albumService.get(albumID), user);
                if(score!=null) model.addAttribute("scoreForm", score);
                else model.addAttribute("scoreForm", new Score());
            }
        } else model.addAttribute("scoreForm", new Score());
        return "Album/Score/create";
    }

    @RequestMapping(value = "/{username}/{albumID}/rateAlbum", method = RequestMethod.POST)
    public String createScore(@PathVariable String username, @PathVariable Long albumID,
                              @ModelAttribute("scoreForm") Score scoreForm, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){
        scoreValidator.validate(scoreForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "Album/Score/create";
        }

        Album album = albumService.get(albumID);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            if(auth.isAuthenticated()) {
                User user = userService.findByUsername(auth.getName());
                Score score = scoreService.getUserScore(album, user);
                if(score != null) {
                    score.setValue(scoreForm.getValue());
                    scoreService.add(score);

                    String successMsg = messageSource.getMessage("messages.score.edit.success",null, LocaleContextHolder.getLocale());
                    redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
                    redirectAttributes.addFlashAttribute("success", successMsg);
                    return "redirect:/{username}/{albumID}";
                }
                scoreForm.setAuthor(user);
                if(scoreForm.getAuthor() == null) return "Album/Score/create";
            }
        }
        scoreForm.setAlbum(album);

        scoreService.add(scoreForm);

        String successMsg = messageSource.getMessage("messages.score.create.success",null, LocaleContextHolder.getLocale());
        redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
        redirectAttributes.addFlashAttribute("success", successMsg);
        return "redirect:/{username}/{albumID}";
    }

    //Read score
    //view: meanScore, userScore
    @RequestMapping(value = "/{username}/{albumID}/rating", method = RequestMethod.GET)
    public String viewScore(@PathVariable String username, @PathVariable Long albumID, Model model,
                            RedirectAttributes redirectAttributes){
        Album album = albumService.get(albumID);
        User user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            if (auth.isAuthenticated()) {
                user = userService.findByUsername(auth.getName());
            }
        }
        if(album!=null) {
            double global = scoreService.getGlobalScore(album);
            model.addAttribute("globalScore", global);
            if(user!=null) {
                Score userScore = scoreService.getUserScore(album, user);
                model.addAttribute("userScore", userScore.getValue());
            } else model.addAttribute("userScore", "Brak oceny");
            return "Album/Score/view";
        }
        String errorMsg = messageSource.getMessage("messages.album.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }

}
