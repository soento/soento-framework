package com.soento.framework.core.support;

import com.soento.framework.core.consts.BaseMessageCode;
import com.soento.framework.core.exception.ClientException;
import com.soento.framework.core.exception.ServiceException;
import com.soento.framework.core.lang.Msg;
import com.soento.framework.core.lang.Resp;
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

    public Resp handlerBindException(HttpServletRequest request, HttpServletResponse response, BindException cause) {
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
        return PojoBuilder.buildResp(Msg.build(String.valueOf(HttpServletResponse.SC_BAD_REQUEST)), request.getLocale());
    }

    public Resp handlerClientException(HttpServletRequest request, ClientException cause) {
        MessageSourceAccessor msa = SpringUtil.getBean(MessageSourceAccessor.class);
        // 输出错误信息
        Msg messageInfo = cause.getMsg();
        StringBuilder msg = new StringBuilder();
        msg.append("== 前端业务异常 ==");
        msg.append(messageInfo.getCode());
        msg.append(":");
        msg.append(messageInfo.getMessage());
        logger.info(msg.toString());
        logger.info(cause.getMessage(), cause);
        // 构造返回值
        return PojoBuilder.buildResp(messageInfo, request.getLocale());
    }

    public Resp handlerServiceException(HttpServletRequest request, ServiceException cause) {
        MessageSourceAccessor msa = SpringUtil.getBean(MessageSourceAccessor.class);
        Msg messageInfo = cause.getMsg();
        // 输出错误信息
        StringBuilder msg = new StringBuilder();
        msg.append("== 后端业务异常 ==");
        msg.append(messageInfo.getCode());
        msg.append(":");
        msg.append(messageInfo.getMessage());
        logger.warn(msg.toString());
        logger.warn(cause.getMessage(), cause);
        // 构造返回值
        return PojoBuilder.buildResp(Msg.build(BaseMessageCode.E9998), request.getLocale());
    }

    public Resp handlerOthers(HttpServletRequest request, Throwable cause) {
        MessageSourceAccessor msa = SpringUtil.getBean(MessageSourceAccessor.class);
        // 输出错误信息
        logger.error("== 未知系统异常 ==" + cause.getMessage(), cause);
        // 构造返回值
        return PojoBuilder.buildResp(Msg.build(BaseMessageCode.E9999), request.getLocale());
    }

}