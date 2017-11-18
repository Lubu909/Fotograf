package pl.edu.ug.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.ug.model.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {
}
