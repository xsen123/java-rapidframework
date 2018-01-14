package com.company.demo.entity;

import go.openus.rapidframework.dao.annotation.NotColumn;
import go.openus.rapidframework.dao.annotation.Table;
import go.openus.rapidframework.entity.BaseEntity;

@Table
public class Detail extends BaseEntity {
    
    private Integer id ;

    private String remark;

    private Integer sampleId;

    //@NotColumn(joinField = "sampleId")
    @NotColumn
    private Sample sample;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSampleId() {
        return sampleId;
    }
    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public Sample getSample() {
        return sample;
    }
    public void setSample(Sample sample) {
        this.sample = sample;
    }

}
