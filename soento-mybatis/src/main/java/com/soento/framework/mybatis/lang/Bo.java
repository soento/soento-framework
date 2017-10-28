package com.soento.framework.mybatis.lang;

import com.soento.framework.core.lang.Pojo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public abstract class Bo extends Pojo {
    @Getter
    @Setter
    protected String id;

    @Getter
    @Setter
    protected String creator;

    @Getter
    @Setter
    protected Date createDate;

    @Getter
    @Setter
    protected Date modifyDate;

    @Getter
    @Setter
    protected String deleteFlag;
}
