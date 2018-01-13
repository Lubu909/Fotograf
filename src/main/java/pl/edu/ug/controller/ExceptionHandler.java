package pl.edu.ug.controller;

import cz.jirutka.rsql.parser.RSQLParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    /*
    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> entityNotFoundHandler(RuntimeException ex, WebRequest request){
        String bodyOfResponse = messageSource.getMessage("messages.notFound", null, LocaleContextHolder.getLocale());
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    */

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    protected String entityNotFoundHandler(EntityNotFoundException ex, HttpServletRequest request){
        String error = messageSource.getMessage("messages.notFound", null, LocaleContextHolder.getLocale());
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null){
            outputFlashMap.put("error", error);
        }
        return "redirect:/";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RSQLParserException.class)
    protected String searchParseError(RSQLParserException ex, HttpServletRequest request){
        String error = messageSource.getMessage("messages.searchError", null, LocaleContextHolder.getLocale());
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null){
            outputFlashMap.put("error", error);
        }
        return "redirect:/advSearch";
    }
}
