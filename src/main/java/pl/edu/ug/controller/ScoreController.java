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
import pl.edu.ug.model.Score;
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

    //Create score
    @RequestMapping(value = "/{username}/{albumID}/rateAlbum", method = RequestMethod.GET)
    public String createScore(@PathVariable String username, @PathVariable Long albumID, Model model){
        model.addAttribute("scoreForm", new Score());
        return "Album/Score/create";
    }

    @RequestMapping(value = "/{username}/{albumID}/rateAlbum", method = RequestMethod.POST)
    public String createScore(@PathVariable String username, @PathVariable Long albumID,
                              @ModelAttribute("scoreForm") Score scoreForm, BindingResult bindingResult){
        //TODO: Validator
        scoreValidator.validate(scoreForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "Album/Score/create";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            if(auth.isAuthenticated()) {
                scoreForm.setAuthor(userService.findByUsername(auth.getName()));
                if(scoreForm.getAuthor() == null) return "Album/Score/create";
            }
        }
        //scoreForm.setAlbum(albumService.get(albumID, userService.findByUsername(username)));
        scoreForm.setAlbum(albumService.get(albumID));

        scoreService.add(scoreForm);

        return "redirect:/" + username + "/" + albumID;
    }

    //Read score
    //view: meanScore, userScore

    //Update score
}
