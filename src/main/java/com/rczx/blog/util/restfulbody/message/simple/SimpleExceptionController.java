package com.rczx.blog.util.restfulbody.message.simple;


import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.rczx.blog.util.MapBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/** @deprecated */
@Deprecated
@RestControllerAdvice
public class SimpleExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(SimpleExceptionController.class);

    public SimpleExceptionController() {
    }

    @ExceptionHandler
    @ResponseBody
    public Map<String, Object> processException(Exception ex) {
        this.logError(ex);
        if(ex instanceof SimpleException) {
            SimpleException errors1 = (SimpleException)ex;
            return this.getErrorMessage(errors1.getCode(), errors1.getMessage(), errors1.getDebug());
        } else {
            List errors;
            if(ex instanceof BindException) {
                errors = ((BindException)ex).getFieldErrors();
                return this.getErrorMessage(Integer.valueOf(400), this.getBindExceptionStr(errors), ex.getMessage());
            } else if(ex instanceof MethodArgumentNotValidException) {
                errors = ((MethodArgumentNotValidException)ex).getBindingResult().getFieldErrors();
                return this.getErrorMessage(Integer.valueOf(400), this.getBindExceptionStr(errors), ex.getMessage());
            } else {
                return ex instanceof HttpMessageNotReadableException?this.getErrorMessage(Integer.valueOf(400), "请发送正确格式的json的数据", ex.getMessage()):this.getErrorMessage(Integer.valueOf(500), "web服务器出现异常", ex.getMessage());
            }
        }
    }

    private void logWarn(Exception ex) {
        logger.warn("SimpleException:-----" + ex.getClass().getSimpleName() + "-" + ex.getMessage());
        logger.debug(ex.getStackTrace()[0].toString());
    }

    private void logError(Exception ex) {
        logger.error("Exception:-----" + ex.getClass().getSimpleName() + "-" + ex.getMessage());
        StackTraceElement[] var2 = ex.getStackTrace();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            StackTraceElement element = var2[var4];
            logger.info(element.toString());
        }

    }

    private Map<String, Object> getErrorMessage(Integer code, String message, String debug) {
        Map error = (new MapBuilder()).add("code", Integer.valueOf(500)).add("message", message).add("debug", debug).build();
        return (new MapBuilder()).add("success", Boolean.valueOf(false)).add("error", error).build();
    }

    private String getBindExceptionStr(List<FieldError> errors) {
        StringBuilder builder = new StringBuilder();
        Iterator var3 = errors.iterator();

        while(var3.hasNext()) {
            FieldError error = (FieldError)var3.next();
            builder.append("， " + error.getDefaultMessage());
        }

        return builder.toString().replaceFirst("， ", "");
    }
}
