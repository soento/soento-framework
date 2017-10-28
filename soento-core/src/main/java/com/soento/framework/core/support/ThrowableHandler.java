package com.soento.framework.core.support;

import com.soento.framework.core.consts.BaseMessageCode;
import com.soento.framework.core.exception.ClientException;
import com.soento.framework.core.exception.ServiceException;
import com.soento.framework.core.lang.MessageInfo;
import com.soento.framework.core.lang.RestResponse;
import com.soento.framework.core.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ThrowableHandler {

    private Logger logger = LoggerFactory.getLogger(ThrowableHandler.class);

    public RestResponse handlerBindException(HttpServletRequest request, HttpServletResponse response, BindException cause) {
        RestResponse resp = new RestResponse();
        MessageSourceAccessor msa = SpringUtil.getBean(MessageSourceAccessor.class);
        // 输出错误信息
        List<ObjectError> errors = cause.getAllErrors();
        for (ObjectError error : errors) {
            StringBuilder msg = new StringBuilder();
            msg.append("== 请求参数异常 ==");
            if (error instanceof FieldError) {
                msg.append("【" + ((FieldError) error).getField() + "】");
            }
            msg.append(error.getCode());
            msg.append(":");
            msg.append(error.getDefaultMessage());
            logger.info(msg.toString());
        }
        logger.info(cause.getMessage(), cause);
        // 构造返回值
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setCode(String.valueOf(HttpServletResponse.SC_BAD_REQUEST));
        resp.setMessage(msa.getMessage(resp.getCode(), request.getLocale()));
        return resp;
    }

    public RestResponse handlerClientException(HttpServletRequest request, ClientException cause) {
        RestResponse resp = new RestResponse();
        MessageSourceAccessor msa = SpringUtil.getBean(MessageSourceAccessor.class);
        // 输出错误信息
        MessageInfo messageInfo = cause.getMessageInfo();
        StringBuilder msg = new StringBuilder();
        msg.append("== 前端业务异常 ==");
        msg.append(messageInfo.getCode());
        msg.append(":");
        msg.append(messageInfo.getMessage());
        logger.info(msg.toString());
        logger.info(cause.getMessage(), cause);
        // 构造返回值
        resp.setCode(messageInfo.getCode());
        resp.setMessage(msa.getMessage(messageInfo.getCode(), messageInfo.getArguments(), request.getLocale()));
        return resp;
    }

    public RestResponse handlerServiceException(HttpServletRequest request, ServiceException cause) {
        RestResponse resp = new RestResponse();
        MessageSourceAccessor msa = SpringUtil.getBean(MessageSourceAccessor.class);
        MessageInfo messageInfo = cause.getMessageInfo();
        // 输出错误信息
        StringBuilder msg = new StringBuilder();
        msg.append("== 后端业务异常 ==");
        msg.append(messageInfo.getCode());
        msg.append(":");
        msg.append(messageInfo.getMessage());
        logger.warn(msg.toString());
        logger.warn(cause.getMessage(), cause);
        // 构造返回值
        resp.setCode(BaseMessageCode.BUSY);
        resp.setMessage(msa.getMessage(resp.getCode(), request.getLocale()));
        return resp;
    }

    public RestResponse handlerOthers(HttpServletRequest request, Throwable cause) {
        RestResponse resp = new RestResponse();
        MessageSourceAccessor msa = SpringUtil.getBean(MessageSourceAccessor.class);
        // 输出错误信息
        logger.error("== 未知系统异常 ==" + cause.getMessage(), cause);
        // 构造返回值
        resp.setCode(BaseMessageCode.SYSTEM_ERROR);
        resp.setMessage(msa.getMessage(resp.getCode(), request.getLocale()));
        return resp;
    }

}