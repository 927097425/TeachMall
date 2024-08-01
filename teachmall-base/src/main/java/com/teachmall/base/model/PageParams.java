package com.teachmall.base.model;


import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class PageParams {
    private Long pageNo = 1L;
    private Long pageSize = 10L;
}
