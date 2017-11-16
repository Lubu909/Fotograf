package pl.edu.ug.service;

import pl.edu.ug.model.Comment;

import java.util.List;

public interface CommentService {
    void add(Comment comment);
    void delete(Comment comment);
    void delete(Long id);
    Comment get(Long id);
    List<Comment> getAll();
}
