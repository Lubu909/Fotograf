package pl.edu.ug.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.edu.ug.model.Comment;

@Component
public class CommentValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Comment.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Comment comment = (Comment) o;

        if(comment.getDescription().length() < 10)
            errors.rejectValue("description", "Description.commentForm.tooShort");
    }
}
