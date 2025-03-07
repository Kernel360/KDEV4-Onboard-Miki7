package com.example.simpleboard.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Api<T> {

    private T body;
    private Pagenation pagination;
}
