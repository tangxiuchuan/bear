package org.bear.admin.bean;

import java.util.List;

public class EasyUIResult<T> {


    //总记录数
    private Long total;

    //查询的数据
    private List<T> rows;

    public EasyUIResult() {
        super();
    }

    public EasyUIResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
