package pl.edu.ug.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.edu.ug.model.Picture;

@Component
public class PictureValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Picture.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Picture picture = (Picture) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Title.pictureForm.Empty");
    }
}
