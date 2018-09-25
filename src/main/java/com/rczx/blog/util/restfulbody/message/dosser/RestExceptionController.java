package com.rczx.blog.util.restfulbody.message.dosser;


import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * @author tangyu
 *         2018/7/28
 */
@RestControllerAdvice
public class RestExceptionController {
    private static Logger logger = LoggerFactory.getLogger(RestExceptionController.class);

    public RestExceptionController() {
    }

    @ExceptionHandler
    public DosserReturnBody errorMessage(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        if(ex instanceof CommonException) {
            logger.info("CommonException:-----" + ex.getClass().getSimpleName() + "-" + ex.getMessage());
            CommonException missEx2 = (CommonException)ex;
            Map errMap2 = this.doWithErrors(missEx2.getErrorMap());
            return missEx2.getMessage().equals("")?(new DosserReturnBodyBuilder()).success(false).status(missEx2.getStatus()).errors(errMap2).build():(new DosserReturnBodyBuilder()).success(false).status(missEx2.getStatus()).message(missEx2.getMessage()).errors(errMap2).build();
        } else {
            List missEx1;
            String errMap1;
            if(ex instanceof BindException) {
                missEx1 = ((BindException)ex).getFieldErrors();
                errMap1 = this.getBindExceptionStr(missEx1);
                return (new DosserReturnBodyBuilder()).success(false).statusBadRequest().message(errMap1).errors(this.getErrorMapFromFieldError(missEx1)).build();
            } else if(ex instanceof MethodArgumentNotValidException) {
                missEx1 = ((MethodArgumentNotValidException)ex).getBindingResult().getFieldErrors();
                errMap1 = this.getBindExceptionStr(missEx1);
                return (new DosserReturnBodyBuilder()).success(false).statusBadRequest().message(errMap1).errors(this.getErrorMapFromFieldError(missEx1)).build();
            } else if(ex instanceof HttpMessageNotReadableException) {
                return (new DosserReturnBodyBuilder()).success(false).statusInternalServerError().message("请求体的格式错误").build();
            } else if(ex instanceof MissingServletRequestParameterException) {
                MissingServletRequestParameterException missEx = (MissingServletRequestParameterException)ex;
                HashMap errMap = new HashMap();
                errMap.put(missEx.getParameterName(), Lists.newArrayList(new String[]{"必须存在"}));
                return (new DosserReturnBodyBuilder()).success(false).statusBadRequest().errors(errMap).build();
            } else {
                logger.error("exception:-----", ex);
                return (new DosserReturnBodyBuilder()).success(false).statusInternalServerError().message("服务器异常#" + ex.getClass().getSimpleName() + "#" + ex.getMessage()).build();
            }
        }
    }

    private Map<String, List<String>> doWithErrors(Map<String, String> map) {
        HashMap errorMap = new HashMap();
        Iterator var3 = map.entrySet().iterator();

        while(var3.hasNext()) {
            Entry entry = (Entry)var3.next();
            errorMap.put(entry.getKey(), Lists.newArrayList(new String[]{(String)entry.getValue()}));
        }

        return errorMap;
    }

    private String getBindExceptionStr(List<FieldError> errorList) {
        StringBuilder builder = new StringBuilder("");
        Iterator msg = errorList.iterator();

        while(msg.hasNext()) {
            FieldError error = (FieldError)msg.next();
            String defaultMessage = error.getDefaultMessage();
            if(defaultMessage != null && defaultMessage.contains("#")) {
                builder.append("， ").append(defaultMessage.replace("#", ""));
            }
        }

        String msg1 = builder.toString().replaceFirst("， ", "");
        if(StringUtils.isEmpty(msg1)) {
            return HttpStatusCode.STATUS_BAD_REQUEST.defaultMessage;
        } else {
            return msg1;
        }
    }

    private Map<String, List<String>> getErrorMapFromFieldError(List<FieldError> errorList) {
        HashMap errorMap = new HashMap();

        FieldError fieldError;
        String defaultMessage;
        for(Iterator var3 = errorList.iterator(); var3.hasNext(); errorMap.put(fieldError.getField(), Lists.newArrayList(new String[]{defaultMessage}))) {
            fieldError = (FieldError)var3.next();
            defaultMessage = fieldError.getDefaultMessage();
            if(!StringUtils.isEmpty(defaultMessage) && defaultMessage.contains("#")) {
                String[] split = defaultMessage.split("#");
                if(split.length > 1) {
                    defaultMessage = split[1];
                }
            }
        }

        return errorMap;
    }
}
