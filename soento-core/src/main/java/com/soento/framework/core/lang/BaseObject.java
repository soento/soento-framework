package com.soento.framework.core.lang;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.soento.framework.core.util.JSONUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class BaseObject implements Serializable {
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String toJSONString() throws JsonProcessingException {
        return JSONUtil.toJSON(this);
    }
}
