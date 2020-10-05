package com.wenqicode.community.advice;

import com.wenqicode.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wenqi Liang
 * @date 2020/10/5
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable ex, Model model) {
        if (ex instanceof CustomizeException) {
            model.addAttribute("message", ex.getMessage());
        }else {
            model.addAttribute("message", "服务冒烟了, 要不然你稍后试试...");
        }
        return new ModelAndView("error");
    }
}
