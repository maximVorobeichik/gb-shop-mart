package ru.gb.gbshopmart.web.dto;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    private String title;
    private Set<ProductDto> products;
}
