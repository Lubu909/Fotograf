package pl.edu.ug.service;

import pl.edu.ug.model.Picture;

import java.util.List;

public interface PictureService {
    void add(Picture picture);
    void delete(Picture picture);
    void delete(Long id);
    Picture get(Long id);
    List<Picture> getAll();
}
