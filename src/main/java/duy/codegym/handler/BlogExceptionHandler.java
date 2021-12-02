package duy.codegym.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class BlogExceptionHandler {

    // sử dụng catch exception handler cho toàn bôj controller và bỏ try catch ở bên controller thì vẫn được
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView exception(IllegalArgumentException e) {
        System.out.println("error" + e.getMessage());
        ModelAndView model = new ModelAndView("/error-404");
        return model;
    }
}
