package com.soento.framework.core.lang;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soento.framework.core.util.JsonUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class Pojo implements Serializable {
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String toJson() throws JsonProcessingException {
        return JsonUtil.toJson(this);
    }

    public String toJsonp(String function) {
        return JsonUtil.toJsonp(function, this);
    }
}
