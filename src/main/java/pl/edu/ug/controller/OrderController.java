package pl.edu.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.ug.java.EmailSender;
import pl.edu.ug.model.Order;
import pl.edu.ug.model.User;
import pl.edu.ug.service.OrderService;
import pl.edu.ug.service.UserService;
import pl.edu.ug.validator.OrderValidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private OrderValidator orderValidator;

    @Autowired
    private EmailSender emailSender;

    private String changeStatus(String username, Long orderID, RedirectAttributes redirectAttributes, int status){
        User user = userService.findByUsername(username);
        if(user != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null) {
                if(auth.isAuthenticated()) {
                    if(auth.getName().equals(username)) {
                        Order order = orderService.get(orderID);
                        if (order != null) {
                            order.setStatus(status);
                            orderService.add(order);

                            if(order.getFotograf() == user){
                                //send mail to klient
                                emailSender.sendNoticeStatusChange(order.getKlient(), status);
                            } else {
                                //send mail to fotograf
                                emailSender.sendNoticeStatusChange(order.getFotograf(), status);
                            }

                            String successMsg;
                            if(status == Order.STATUS_ACCEPTED) successMsg = messageSource.getMessage("messages.order.accept",null, LocaleContextHolder.getLocale());
                            else successMsg = messageSource.getMessage("messages.order.reject",null, LocaleContextHolder.getLocale());
                            redirectAttributes.addAttribute("username", username);
                            redirectAttributes.addFlashAttribute("success", successMsg);
                            return "redirect:/{username}/orders";
                        }
                    }
                }
            }
        }
        String errorMsg = messageSource.getMessage("messages.order.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }

    //Create Order
    @RequestMapping(value = "/{username}/order", method = RequestMethod.GET)
    public String createOrder(Model model, RedirectAttributes redirectAttributes,
                              @PathVariable String username){
        User fotograf = userService.findByUsername(username);
        if(fotograf!=null) {
            model.addAttribute("orderForm", new Order());
            return "Order/create";
        }
        String errorMsg = messageSource.getMessage("messages.user.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }

    @RequestMapping(value = "/{username}/order", method = RequestMethod.POST)
    public String createOrder(@ModelAttribute("orderForm") Order orderForm, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, @PathVariable String username){
        User fotograf = userService.findByUsername(username);
        if(fotograf!=null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                if (auth.isAuthenticated()) {
                    if (!auth.getName().equals(username)) {
                        try {
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", LocaleContextHolder.getLocale());
                            orderForm.setTerminWykonania(format.parse(orderForm.getDateForm() + " " + orderForm.getTimeForm()));
                        } catch (ParseException e) {}
                        orderValidator.validate(orderForm, bindingResult);

                        if (bindingResult.hasErrors()) {
                            //modelMap.put(BindingResult.class.getName() + ".orderForm", bindingResult);
                            return "Order/create";
                            //redirectAttributes.addAttribute("username", username);
                            //return "redirect:/{username}/order";
                        }

                        orderForm.setFotograf(fotograf);
                        orderForm.setKlient(userService.findByUsername(auth.getName()));
                        orderForm.setStatus(Order.STATUS_CREATED);
                        orderService.add(orderForm);

                        emailSender.sendNoticeOrderCreated(orderForm.getFotograf());
                        emailSender.sendNoticeOrderCreated(orderForm.getKlient());

                        String successMsg = messageSource.getMessage("messages.order.create.success", null, LocaleContextHolder.getLocale());
                        redirectAttributes.addAttribute("username", auth.getName());
                        redirectAttributes.addFlashAttribute("success", successMsg);
                        return "redirect:/{username}/orders";
                    }
                }
            }
        }
        String errorMsg = messageSource.getMessage("messages.user.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }

    //Update Order

    //Accept Order
    @RequestMapping(value = "/{username}/order/{orderID}/accept", method = RequestMethod.POST)
    public String acceptOrder(@PathVariable String username, @PathVariable Long orderID,
                              RedirectAttributes redirectAttributes){
        return changeStatus(username, orderID, redirectAttributes, Order.STATUS_ACCEPTED);
    }

    //Reject Order
    @RequestMapping(value = "/{username}/order/{orderID}/reject", method = RequestMethod.POST)
    public String rejectOrder(@PathVariable String username, @PathVariable Long orderID,
                              RedirectAttributes redirectAttributes){
        return changeStatus(username, orderID, redirectAttributes, Order.STATUS_REJECTED);
    }

    //Change Order
    @RequestMapping(value = "/{username}/order/{orderID}/edit", method = RequestMethod.GET)
    public String changeOrder(Model model, RedirectAttributes redirectAttributes,
                              @PathVariable String username, @PathVariable Long orderID){
        User fotograf = userService.findByUsername(username);
        if(fotograf!=null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null) {
                if (auth.isAuthenticated()) {
                    if (auth.getName().equals(username)) {
                        Order order = orderService.get(orderID);
                        if(order!=null) {
                            //data do formularza
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", LocaleContextHolder.getLocale());
                            order.setDateForm(dateFormat.format(order.getTerminWykonania()));
                            //godzina do formularza
                            DateFormat timeFormat = new SimpleDateFormat("HH:mm", LocaleContextHolder.getLocale());
                            order.setTimeForm(timeFormat.format(order.getTerminWykonania()));
                            order.setDescription("");
                            model.addAttribute("orderForm", order);
                            return "Order/create";
                        }
                        else{
                            String errorMsg = messageSource.getMessage("messages.order.notFound",null, LocaleContextHolder.getLocale());
                            redirectAttributes.addFlashAttribute("error", errorMsg);
                            redirectAttributes.addAttribute("username", username);
                            return "redirect:/{username}/orders";
                        }
                    }
                }
            }
        }
        String errorMsg = messageSource.getMessage("messages.user.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }

    @RequestMapping(value = "/{username}/order/{orderID}/edit", method = RequestMethod.POST)
    public String changeOrder(@ModelAttribute("orderForm") Order orderForm, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                              @PathVariable String username, @PathVariable Long orderID){
        User fotograf = userService.findByUsername(username);
        if(fotograf!=null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null) {
                if (auth.isAuthenticated()) {
                    if (auth.getName().equals(username)) {
                        try{
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", LocaleContextHolder.getLocale());
                            orderForm.setTerminWykonania(format.parse(orderForm.getDateForm() + " " + orderForm.getTimeForm()));
                        } catch (ParseException e) {}
                        orderValidator.validate(orderForm, bindingResult);

                        if (bindingResult.hasErrors()) {
                            return "Order/create";
                            //redirectAttributes.addAttribute("username", username);
                            //return "redirect:/{username}/order";
                        }

                        Order order = orderService.get(orderID);
                        if(order != null) {
                            order.setTerminWykonania(orderForm.getTerminWykonania());
                            order.setHours(orderForm.getHours());
                            order.setDescription(order.getDescription() + "\n<br/>" + orderForm.getDescription());

                            order.setStatus(Order.STATUS_MODIFIED);

                            orderService.add(order);

                            emailSender.sendNoticeStatusChange(order.getKlient(), Order.STATUS_MODIFIED);

                            String successMsg = messageSource.getMessage("messages.order.change", null, LocaleContextHolder.getLocale());
                            redirectAttributes.addAttribute("username", username);
                            redirectAttributes.addFlashAttribute("success", successMsg);
                            return "redirect:/{username}/orders";
                        } else {
                            String errorMsg = messageSource.getMessage("messages.order.notFound",null, LocaleContextHolder.getLocale());
                            redirectAttributes.addFlashAttribute("error", errorMsg);
                            redirectAttributes.addAttribute("username", username);
                            return "redirect:/{username}/orders";
                        }
                    }
                }
            }
        }
        String errorMsg = messageSource.getMessage("messages.user.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }

    //Get Order
    @RequestMapping(value = "/{username}/order/{orderID}", method = RequestMethod.GET)
    public String viewOrder(Model model, RedirectAttributes redirectAttributes,
                            @PathVariable String username, @PathVariable Long orderID){
        User fotograf = userService.findByUsername(username);
        if(fotograf!=null) {
            Order order = orderService.get(orderID);
            if(order!=null) {
                model.addAttribute("order", order);
                model.addAttribute("endTime", new Date(order.getTerminWykonania().getTime() + order.getHours()*3600*1000));
                return "Order/view";
            } else {
                String errorMsg = messageSource.getMessage("messages.order.notFound",null, LocaleContextHolder.getLocale());
                redirectAttributes.addFlashAttribute("error", errorMsg);
                redirectAttributes.addAttribute("username", username);
                return "redirect:/{username}/orders";
            }
        }
        String errorMsg = messageSource.getMessage("messages.user.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }

    //List Orders
    @RequestMapping(value = "/{username}/orders", method = RequestMethod.GET)
    public String listOrders(Model model, RedirectAttributes redirectAttributes,
                             @PathVariable String username){
        User user = userService.findByUsername(username);
        if(user!=null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null) {
                if (auth.isAuthenticated()) {
                    if (auth.getName().equals(username)) {
                        List<Order> orders;
                        if (user.containsRole(User.ROLE_PHOTOGRAPHER)) {
                            //zamówienia fotografa
                            orders = user.getOrdersReceived();
                            //List<Order> accepted =
                        } else {
                            //zamówienia klienta
                            orders = user.getOrdersMade();
                        }
                        orders.sort(Comparator.comparing(Order::getDataZamowienia).reversed());
                        model.addAttribute("orders", orders);
                        return "Order/list";
                    }
                }
            }
        }
        String errorMsg = messageSource.getMessage("messages.user.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }

    //Delete Order
    @RequestMapping(value = "/{username}/order/{orderID}/delete", method = RequestMethod.POST)
    public String deleteOrder(RedirectAttributes redirectAttributes,
                              @PathVariable String username, @PathVariable Long orderID){
        User fotograf = userService.findByUsername(username);
        if(fotograf!=null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null) {
                if (auth.isAuthenticated()) {
                    if (auth.getName().equals(username)) {
                        Order order = orderService.get(orderID);
                        if(order!=null) {
                            orderService.delete(order);

                            String successMsg = messageSource.getMessage("messages.order.delete.success", null, LocaleContextHolder.getLocale());
                            redirectAttributes.addAttribute("username", username);
                            redirectAttributes.addFlashAttribute("success", successMsg);
                            return "redirect:/{username}/orders";
                        } else {
                            String errorMsg = messageSource.getMessage("messages.order.notFound",null, LocaleContextHolder.getLocale());
                            redirectAttributes.addFlashAttribute("error", errorMsg);
                            redirectAttributes.addAttribute("username", username);
                            return "redirect:/{username}/orders";
                        }
                    }
                }
            }
        }
        String errorMsg = messageSource.getMessage("messages.user.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }
}
