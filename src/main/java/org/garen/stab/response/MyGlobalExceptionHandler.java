package org.garen.stab.response;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    @ResponseBody
    public Response BusinessExceptionHandler(Exception e) {
        e.printStackTrace();
        return new Response(CodeEnum.BUSINESS_EXCEPTION.code(), CodeEnum.BUSINESS_EXCEPTION.msg(), e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response ExceptionHandler(Exception e) {
        e.printStackTrace();
        return new Response(CodeEnum.SYSTEM_EXCEPTION.code(), CodeEnum.SYSTEM_EXCEPTION.msg(), e.getMessage());
    }

}
