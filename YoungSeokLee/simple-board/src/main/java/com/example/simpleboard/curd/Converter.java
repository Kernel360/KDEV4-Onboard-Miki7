package com.example.simpleboard.curd;

import javax.swing.text.html.parser.Entity;

public interface Converter<DTO, ENTITY> {

    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);
}
