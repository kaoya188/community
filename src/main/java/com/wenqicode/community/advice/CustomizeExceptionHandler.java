package com.wenqicode.community.advice;

import com.alibaba.fastjson.JSON;
import com.wenqicode.community.dto.ResultDTO;
import com.wenqicode.community.exception.CustomizeErrorCode;
import com.wenqicode.community.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Wenqi Liang
 * @date 2020/10/5
 */
@ControllerAdvice
@Slf4j
public class CustomizeExceptionHandler {
    /**
     * 根据不同请求, 返回不同的异常处理
     *
     * @param request
     * @param ex
     * @param model
     * @return
     */
    @ExceptionHandler(Exception.class)
    Object handleControllerException(HttpServletRequest request, HttpServletResponse response, Throwable ex, Model model) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            PrintWriter writer;
            try {
                // 返回Json
                if (ex instanceof CustomizeException) {
                    resultDTO = ResultDTO.errorOf((CustomizeException) ex);
                }else {
                    log.error("handle error", ex);
                    resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
                }
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
            }
            return null;
        } else {
            // 返回错误页面
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                log.error("handle error", ex);
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
