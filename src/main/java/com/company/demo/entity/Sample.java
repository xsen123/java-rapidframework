package com.company.demo.entity;

import go.openus.rapidframework.dao.annotation.Table;
import go.openus.rapidframework.entity.BaseEntity;

@Table
//@Table("sample")
//@Table(name = "sample")
//@Table(name = "sample", pkColumnName = "pkid") // pkColumnName默认为id
public class Sample extends BaseEntity {
    
    private Integer id ;

    /**
     * 姓名
     */
    private String name;

    /**
     * 金额
     */
    private Double money;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

}
