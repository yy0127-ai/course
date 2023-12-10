package com.course.business.controller;

import com.course.server.dto.ResponseDto;
import com.course.server.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {
    //打印输出日志
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public ResponseDto validatorExceptionHandler(ValidatorException e){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
//        responseDto.setMessage(e.getMessage());//此时不将message返回给前端，因为防止别人进行探测，那么就会知道该网站的属性要求
        responseDto.setMessage("请求参数异常！");
        LOG.warn(e.getMessage());
        return responseDto;
    }
}
