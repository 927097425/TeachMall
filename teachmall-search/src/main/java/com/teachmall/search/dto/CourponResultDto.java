package com.teachmall.search.dto;


import com.teachmall.base.model.PageResult;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
public class CourponResultDto<T> extends PageResult {
    public CourponResultDto(List<T> items, long counts, long page, long pageSize) {
        super(items, counts, page, pageSize);
    }

}
