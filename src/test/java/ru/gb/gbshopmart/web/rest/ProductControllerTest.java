package ru.gb.gbshopmart.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbapi.common.enums.Status;
import ru.gb.gbapi.manufacturer.dto.ManufacturerDto;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbshopmart.dao.CategoryDao;
import ru.gb.gbshopmart.entity.web.dto.mapper.CategoryMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryMapper categoryMapper;

    @Test
    @Order(1)
    void testSaveProductTest() throws Exception {

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title("phones")
                                        .build())))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/manufacturer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ManufacturerDto.builder()
                                        .name("Samsung")
                                        .build())))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ProductDto.builder()
                                        .title("S22")
                                        .cost(new BigDecimal(100.00))
                                        .manufactureDate(LocalDate.now())
                                        .manufacturer("Samsung")
                                        .categories(Set.of(categoryMapper.toCategoryDto(categoryDao.findById(1L).get())))
                                        .status(Status.ACTIVE)
                                        .build())))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void findAllTest() throws Exception {

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("S22"));
    }

    @Test
    @Order(3)
    public void findByIdTest() throws Exception {

        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("S22"));
    }

    @Test
    @Order(4)
    public void updateProductTest() throws Exception{

        mockMvc.perform(put("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ProductDto.builder()
                                        .title("S21")
                                        .cost(new BigDecimal(100.00))
                                        .manufactureDate(LocalDate.now())
                                        .manufacturer("Samsung")
                                        .categories(Set.of(categoryMapper.toCategoryDto(categoryDao.findById(1L).get())))
                                        .status(Status.ACTIVE)
                                        .build())
                        ))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("S21"));
    }


    @Test
    @Order(5)
    public void deleteByIdTest() throws Exception {

        mockMvc.perform(delete("/api/v1/product/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isNotFound());

    }

    @Test
    @Order(6)
    void testSaveProductNoSuccessTest() throws Exception {

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ProductDto.builder()
                                        .title("S22")
                                        .cost(new BigDecimal(100.00))
                                        .manufactureDate(LocalDate.now())
                                        .manufacturer("Apple")
                                        .categories(Set.of(categoryMapper.toCategoryDto(categoryDao.findById(1L).get())))
                                        .status(Status.ACTIVE)
                                        .build())))
                .andExpect(status().isConflict());
    }

}