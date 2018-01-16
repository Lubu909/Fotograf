package pl.edu.ug.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.edu.ug.model.Album;
import pl.edu.ug.service.AlbumService;

@Component
public class AlbumValidator implements Validator {

    @Autowired
    private AlbumService albumService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Album.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Album album = (Album) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "You have to name the album!");
        if(album.getName().length() < 3 || album.getName().length() > 255)
            errors.rejectValue("name", "Size.albumForm.name");

        /*
        if(albumService.findByName(album.getName()) != null)
            errors.rejectValue("name", "Duplicate.albumForm.name");
            */
    }
}
