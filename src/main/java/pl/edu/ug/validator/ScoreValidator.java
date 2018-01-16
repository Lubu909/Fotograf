package pl.edu.ug.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.edu.ug.model.Score;

@Component
public class ScoreValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Score.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Score score = (Score) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "Score cannot be empty!");

        if(score.getValue() < 0 || score.getValue() > 10)
            errors.rejectValue("value", "Value.scoreForm.incorrectRange");
    }
}
