package pl.edu.ug.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.edu.ug.model.Picture;

import java.util.Objects;

@Component
public class PictureValidator implements Validator {
    private static final String MIME_IMAGE_JPEG        = "image/jpeg";
    private static final String MIME_IMAGE_PNG         = "image/png";

    @Override
    public boolean supports(Class<?> aClass) {
        return Picture.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Picture picture = (Picture) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Title.pictureForm.Empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "photoFile", "PhotoFile.pictureForm.Empty");
        if(picture.getPhotoFile().getSize() > 20971520) errors.rejectValue("photoFile", "PhotoFile.pictureForm.Size");
        String contentType = picture.getPhotoFile().getContentType();
        if(!contentType.equals(MIME_IMAGE_JPEG) && !contentType.equals(MIME_IMAGE_PNG))
            errors.rejectValue("photoFile","PhotoFile.pictureForm.Type");
    }

    public void validateEdit(Object o, Errors errors){
        Picture picture = (Picture) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Title.pictureForm.Empty");
    }
}
