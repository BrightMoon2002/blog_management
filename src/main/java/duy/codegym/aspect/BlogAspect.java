package duy.codegym.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class BlogAspect {


    //Sử dụng các anotation trên phương thức pointcut để sử dụng advice
    @Before(value = "exceuteController()")
    public void beforeExecuteController() {
        System.out.println("Before Controller");
    }

    @After(value = "exceuteController()")
    public void afterExecuteController(){
        System.out.println("After Controller");
    }

    //tạo 1 pointcut trỏ đến tất cả class trong controller thì dùng * hehe
   @Pointcut(value = "within(duy.codegym.controller.*)")
   public void exceuteController(){}
}
