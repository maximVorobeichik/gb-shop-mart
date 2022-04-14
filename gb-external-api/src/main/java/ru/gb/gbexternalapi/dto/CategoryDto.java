package ru.gb.gbexternalapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    @JsonProperty(value = "id")
    private Long categoryId;
    private String name;
}