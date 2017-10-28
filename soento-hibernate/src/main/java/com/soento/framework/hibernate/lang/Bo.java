package com.soento.framework.hibernate.lang;

import com.soento.framework.core.lang.Pojo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

public abstract class Bo extends Pojo {
    @Getter
    @Setter
    @Column(name = "CREATOR")
    protected String creator;

    @Getter
    @Setter
    @Column(name = "CREATE_DATE")
    protected Date createDate;

    @Getter
    @Setter
    @Column(name = "MODIFY_DATE")
    protected Date modifyDate;

    @Getter
    @Setter
    @Column(name = "DELETE_FLAG")
    protected String deleteFlag;
}
