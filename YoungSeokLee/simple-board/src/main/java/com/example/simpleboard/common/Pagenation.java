package com.example.simpleboard.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagenation {
    private Integer page;
    private Integer size;

    private Integer currentElements;
    private Integer totalPage;
    private Long totalElements;
}
