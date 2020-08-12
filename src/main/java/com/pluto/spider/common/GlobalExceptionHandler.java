package com.pluto.spider.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Descrintion 统一异常处理
 * @Date create 2018-5-2 15:54
 * @Version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";
    /**
     * 统一异常处理
     *
     * @param request
     * @param response
     * @param object
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) {
        // 判断是否ajax请求
        if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                .getHeader("X-Requested-With") != null && request.getHeader(
                "X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            // 如果不是ajax，模板渲染格式返回
            // 为安全起见，只有业务异常我们对前端可见，否则否则统一归为系统异常
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            if (exception instanceof Object) {
                map.put("errorMsg", exception.getMessage());
            } else {
                map.put("errorMsg", "系统异常！");
            }
            //这里需要手动将异常打印出来，由于没有配置log，实际生产环境应该打印到log里面
            exception.printStackTrace();
            //对于非ajax请求，我们都统一跳转到error页面
            return new ModelAndView("/error", map);
        } else {
            // 如果是ajax请求，JSON格式返回
            try {
                //为了方便查看ajax请求访问的异常，也将异常信息打印至控制台，实际生产环境应该打印到log里面
                exception.printStackTrace();
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("success", false);
                // 为安全起见，只有业务异常我们对前端可见，否则统一归为系统异常
                if (exception instanceof Object) {
                    map.put("errorMsg", exception.getMessage());
                } else {
                    map.put("errorMsg", "系统异常！");
                }
                writer.write(map.toString());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
