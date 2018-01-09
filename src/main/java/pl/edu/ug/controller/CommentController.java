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
import pl.edu.ug.model.Comment;
import pl.edu.ug.model.User;
import pl.edu.ug.service.AlbumService;
import pl.edu.ug.service.CommentService;
import pl.edu.ug.service.UserService;
import pl.edu.ug.validator.CommentValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CommentValidator commentValidator;

    @Autowired
    private MessageSource messageSource;

    //Create&Update comment
    @RequestMapping(value = "/{username}/{albumID}/comment", method = RequestMethod.GET)
    public String writeComment(@PathVariable String username, @PathVariable Long albumID, Model model){
        model.addAttribute("commentForm", new Comment());
        return "Album/Comment/create";
    }

    @RequestMapping(value = "/{username}/{albumID}/comment", method = RequestMethod.POST)
    public String writeComment(@PathVariable String username, @PathVariable Long albumID,
                               @ModelAttribute("commentForm") Comment commentForm, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        commentValidator.validate(commentForm, bindingResult);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentForm", bindingResult);
            redirectAttributes.addFlashAttribute("commentForm", commentForm);
            return "redirect:/{username}/{albumID}";
        }

        Album album = albumService.get(albumID);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            if(auth.isAuthenticated()) {
                User user = userService.findByUsername(auth.getName());
                commentForm.setAuthor(user);
                if(commentForm.getAuthor() == null) return "Album/Comment/create";
            }
        }
        commentForm.setAlbum(album);

        commentService.add(commentForm);

        String successMsg = messageSource.getMessage("messages.comment.create.success",null, LocaleContextHolder.getLocale());
        redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
        redirectAttributes.addFlashAttribute("success", successMsg);
        return "redirect:/{username}/{albumID}";
    }

    @RequestMapping(value = "/{username}/{albumID}/comment/{commentID}", method = RequestMethod.GET)
    public String editComment(@PathVariable String username, @PathVariable Long albumID, @PathVariable Long commentID,
                              Model model, RedirectAttributes redirectAttributes){
        try {
            Comment comment = commentService.get(commentID);
            if (comment.getId() != null) {
                model.addAttribute("commentForm", comment);
                return "Album/Comment/create";
            }
        } catch (Exception e) {
            String errorMsg = messageSource.getMessage("messages.comment.notFound", null, LocaleContextHolder.getLocale());
            redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/{username}/{albumID}";
        }
        String errorMsg = messageSource.getMessage("messages.comment.notFound", null, LocaleContextHolder.getLocale());
        redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/{username}/{albumID}";
    }

    @RequestMapping(value = "/{username}/{albumID}/comment/{commentID}", method = RequestMethod.POST)
    public String editComment(@PathVariable String username, @PathVariable Long albumID, @PathVariable Long commentID,
                              @ModelAttribute("commentForm") Comment commentForm, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){
        commentValidator.validate(commentForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "Album/Comment/create";
        }

        Album album = albumService.get(albumID);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            if(auth.isAuthenticated()) {
                User user = userService.findByUsername(auth.getName());
                Comment comment = commentService.get(commentID);
                if(comment != null) {
                    comment.setDescription(commentForm.getDescription());
                    commentService.add(comment);

                    String successMsg = messageSource.getMessage("messages.comment.edit.success",null, LocaleContextHolder.getLocale());
                    redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
                    redirectAttributes.addFlashAttribute("success", successMsg);
                    return "redirect:/{username}/{albumID}";
                }
                commentForm.setAuthor(user);
                if(commentForm.getAuthor() == null) return "Album/Comment/create";
            }
        }

        String errorMsg = messageSource.getMessage("messages.comment.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/{username}/{albumID}";
    }

    //Read comment
    @RequestMapping(value = "/{username}/{albumID}/commentList", method = RequestMethod.GET)
    public String listComments(@PathVariable String username, @PathVariable Long albumID, Model model,
                               RedirectAttributes redirectAttributes){
        Album album = albumService.get(albumID);
        if(album!=null) {
            model.addAttribute("album", album);
            List<Comment> commentList = commentService.getCommentList(albumService.get(albumID));
            model.addAttribute("comments", commentList);
            return "Album/Comment/list";
        }
        String errorMsg = messageSource.getMessage("messages.album.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }

    //Delete comment
    @RequestMapping(value = "/{username}/{albumID}/commentList/{commentID}", method = RequestMethod.POST)
    public String deleteComment(@PathVariable String username, @PathVariable Long albumID, @PathVariable Long commentID,
                                RedirectAttributes redirectAttributes){
        User user = userService.findByUsername(username);
        if(user != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null) {
                if(auth.isAuthenticated()) {
                    if(auth.getName().equals(username)) {
                        Comment comment = commentService.get(commentID);
                        if (comment != null) commentService.delete(comment);
                    }
                }
            }
        }

        String successMsg = messageSource.getMessage("messages.comment.delete.success",null, LocaleContextHolder.getLocale());
        redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
        redirectAttributes.addFlashAttribute("success", successMsg);
        return "redirect:/{username}/{albumID}";
    }
}
