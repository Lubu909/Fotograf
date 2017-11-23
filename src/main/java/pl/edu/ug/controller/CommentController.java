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
import pl.edu.ug.model.Comment;
import pl.edu.ug.model.User;
import pl.edu.ug.service.AlbumService;
import pl.edu.ug.service.CommentService;
import pl.edu.ug.service.UserService;
import pl.edu.ug.validator.CommentValidator;

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

    //Create&Update comment
    @RequestMapping(value = "/{username}/{albumID}/comment", method = RequestMethod.GET)
    public String writeComment(@PathVariable String username, @PathVariable Long albumID, Model model){
        User user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            if (auth.isAuthenticated()) {
                user = userService.findByUsername(auth.getName());
                Comment comment = commentService.getComment(albumService.get(albumID), user);
                if(comment!=null) model.addAttribute("commentForm", comment);
                else model.addAttribute("commentForm", new Comment());
            }
        } else model.addAttribute("commentForm", new Comment());
        return "Album/Comment/create";
    }

    @RequestMapping(value = "/{username}/{albumID}/comment", method = RequestMethod.POST)
    public String writeComment(@PathVariable String username, @PathVariable Long albumID,
                               @ModelAttribute("commentForm") Comment commentForm, BindingResult bindingResult){
        commentValidator.validate(commentForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "Album/Comment/create";
        }

        Album album = albumService.get(albumID);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            if(auth.isAuthenticated()) {
                User user = userService.findByUsername(auth.getName());
                Comment comment = commentService.getComment(album, user);
                if(comment != null) {
                    comment.setDescription(commentForm.getDescription());
                    commentService.add(comment);
                    return "redirect:/" + username + "/" + albumID;
                }
                commentForm.setAuthor(user);
                if(commentForm.getAuthor() == null) return "Album/Comment/create";
            }
        }
        commentForm.setAlbum(album);

        commentService.add(commentForm);

        return "redirect:/" + username + "/" + albumID;
    }

    //Read comment
    @RequestMapping(value = "/{username}/{albumID}/commentList", method = RequestMethod.GET)
    public String listComments(@PathVariable String username, @PathVariable Long albumID, Model model){
        Album album = albumService.get(albumID);
        if(album!=null) {
            model.addAttribute("album", album);
            List<Comment> commentList = commentService.getCommentList(albumService.get(albumID));
            model.addAttribute("comments", commentList);
            return "Album/Comment/list";
        }
        return "Album/notFound";
    }

    //Delete comment
    @RequestMapping(value = "/{username}/{albumID}/commentList/{commentID}", method = RequestMethod.POST)
    public String deleteComment(@PathVariable String username, @PathVariable Long albumID, @PathVariable Long commentID){
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
        return "redirect:/" + username + "/" + albumID + "/commentList";
    }
}
