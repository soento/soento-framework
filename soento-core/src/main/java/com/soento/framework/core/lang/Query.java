package com.soento.framework.core.lang;

import lombok.Getter;

public class Query extends Pojo {
    /**
     * 查询页数
     */
    @Getter
    protected int index;
    /**
     * 分页步长
     */
    @Getter
    protected int limit;
    /**
     * 开始位置
     */
    @Getter
    protected int start;
    /**
     * 结束位置
     */
    @Getter
    protected int end;

    public Query() {
        setIndex(0);
        setLimit(10);
    }

    public Query(int index, int limit) {
        setIndex(index);
        setLimit(limit);
    }

    public void setIndex(int index) {
        this.index = index;
        setStart();
        setEnd();
    }

    public void setLimit(int limit) {
        this.limit = limit;
        setStart();
        setEnd();
    }

    private void setStart() {
        this.start = this.index * this.limit;
    }

    private void setEnd() {
        this.end = this.start + this.limit - 1;
    }
}
