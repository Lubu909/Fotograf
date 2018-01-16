package pl.edu.ug.service;

import pl.edu.ug.model.Album;
import pl.edu.ug.model.Comment;
import pl.edu.ug.model.User;

import java.util.List;

public interface CommentService {
    void add(Comment comment);
    void delete(Comment comment);
    void delete(Long id);
    Comment get(Long id);
    Comment getComment(Album album, User author);
    List<Comment> getCommentList(Album album);
    List<Comment> getAll();
}
