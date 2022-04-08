package ru.gb.gbshopmart.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.entity.Manufacturer;
import ru.gb.gbshopmart.service.CategoryService;
import ru.gb.gbshopmart.web.dto.CategoryDto;
import ru.gb.gbshopmart.web.dto.ManufacturerDto;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerMockitoTest {
    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    List<CategoryDto> categorys = new ArrayList<>();

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        categorys.add(CategoryDto.builder().id(1L).title("s").build());
        categorys.add(CategoryDto.builder().id(2L).title("s1").build());

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }
    @Test
    void mockMvcGetManufacturerListTest() throws Exception {

        given(categoryService.findAll()).willReturn(categorys);

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("s"));
              //  .andExpect(jsonPath("$.[1].id").value("2"));

    }
}
