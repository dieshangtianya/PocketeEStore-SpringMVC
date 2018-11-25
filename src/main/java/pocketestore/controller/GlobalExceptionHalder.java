package pocketestore.controller;

import org.apache.logging.log4j.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pocketestore.infrastructure.JsonResponseBuilder;
import pocketestore.infrastructure.exceptions.BusinessException;
import pocketestore.infrastructure.utils.JacksonHelper;
import pocketestore.infrastructure.utils.RequestHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

//使用ControllerAdvice全局处理的是使用了RequestMapping的Controller抛出的异常
//因此此处的异常处理程序都来自于客户端的调用
@ControllerAdvice
public class GlobalExceptionHalder {
    private static final Logger logger = LogManager.getLogger("storeLog");

    //处理所有未知的异常,因为请求的方式不确定
    //(1)如果是资源访问，发生404等需要返回一个错误页面
    //(2)如果是ajax请求，那么则应该返回相关的JSON数据
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        if (RequestHelper.isAjaxRequest(request)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            try {
                PrintWriter writer = response.getWriter();
                String errorMsg = ex.getMessage();
                Map<String, Object> responseObj = JsonResponseBuilder.buildErrorResponse(errorMsg);
                String jsonString = JacksonHelper.toJSon(responseObj);
                writer.write(jsonString);
                writer.flush();
                writer.close();
                logger.error(errorMsg);//记录日志
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return null;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/error");
            return modelAndView;
        }
    }


    //对于所有其他的关于业务的异常，都使用该处理程序处理
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Map<String, Object> handleException(BusinessException ex) {
        String errorMsg = ex.getMessage();
        Map<String, Object> responseObj = JsonResponseBuilder.buildErrorResponse(errorMsg);
        logger.error(errorMsg);
        return responseObj;
    }
}
