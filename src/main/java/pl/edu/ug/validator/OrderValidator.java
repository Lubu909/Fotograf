package pl.edu.ug.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.edu.ug.model.Order;

import java.util.Date;

@Component
public class OrderValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return Order.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Order order = (Order) target;

        //hours limit >0, <week
        if(order.getHours() <= 0 || order.getHours() > 168) errors.rejectValue("hours", "OrderForm.hoursLimit");
        //description upper limit
        if(order.getDescription().length() > 255) errors.rejectValue("description", "OrderForm.descriptionLength");
        //terminWykonania != null
        if(order.getTerminWykonania() == null) errors.rejectValue("terminWykonania", "OrderForm.terminWykonaniaNull");
        //terminWykonania > today
        else if(order.getTerminWykonania().before(new Date())) errors.rejectValue("terminWykonania", "OrderForm.terminWykonaniaBeforeNow");
    }
}
