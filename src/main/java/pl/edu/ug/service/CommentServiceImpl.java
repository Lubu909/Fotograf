package pl.edu.ug.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.ug.dao.CommentDao;
import pl.edu.ug.model.Album;
import pl.edu.ug.model.Comment;
import pl.edu.ug.model.User;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public void add(Comment comment) {
        commentDao.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentDao.delete(comment);
    }

    @Override
    public void delete(Long id) {
        commentDao.delete(id);
    }

    @Override
    public Comment get(Long id) {
        return commentDao.getOne(id);
    }

    @Override
    public Comment getComment(Album album, User author) {
        return commentDao.getByAlbumAndAuthor(album,author);
    }

    @Override
    public List<Comment> getCommentList(Album album) {
        return commentDao.getByAlbum(album);
    }

    @Override
    public List<Comment> getAll() {
        return commentDao.findAll();
    }
}
