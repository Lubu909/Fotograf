package pl.edu.ug.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.ug.model.Album;
import pl.edu.ug.model.Comment;
import pl.edu.ug.model.User;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Long> {
    List<Comment> getByAlbum(Album album);
    Comment getByAlbumAndAuthor(Album album, User author);
}
