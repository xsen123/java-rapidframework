package go.openus.rapidframework.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * @param <T> JavaBean对象类型
 */
public class Pager<T> implements Serializable{

    private int pageNumber;     // 页码
    private int pageSize;       // 每页条数
    private long totalRecord;   // 总记录数
    private long totalPage;     // 总页面数
    private List<T> recordList; // 数据列表

    /**
     * 分页无参构造
     */
    public Pager() {
    }

    /**
     * 分页带参数构造
     * @param pageNumber 页码
     * @param pageSize 每页条数
     * @param totalRecord 总记录数
     */
    public Pager(int pageNumber, int pageSize, long totalRecord){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        if (pageSize != 0) {
            totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
        }
    }

    /**
     * 分页带参数构造
     * @param pageNumber 页码
     * @param pageSize 每页条数
     * @param totalRecord 总记录数
     * @param recordList 数据列表
     */
    public Pager(int pageNumber, int pageSize, long totalRecord, List<T> recordList) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.recordList = recordList;
        if (pageSize != 0) {
            totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
        }
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public List<T> getRecordList() {
        return recordList;
    }

    // ----------------------------------------------------------------------------------------------------

    public boolean isFirstPage() {
        return pageNumber == 1;
    }

    public boolean isLastPage() {
        return pageNumber == totalPage;
    }

    public boolean isPrevPage() {
        return pageNumber > 1 && pageNumber <= totalPage;
    }

    public boolean isNextPage() {
        return pageNumber < totalPage;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
