package com.example.demo.service;

import com.example.demo.dto.ItemRequestDto;
import com.example.demo.dto.ItemResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Test
    void shouldLoadAllSeededItems() {
        List<ItemResponseDto> items = itemService.findAll();
        assertThat(items).hasSize(5);
    }

    @Test
    void shouldFindById() {
        ItemResponseDto dto = itemService.findById(1L);
        assertThat(dto.getName()).isEqualTo("iPhone 15");
    }

    @Test
    @Transactional
    void shouldCreateNewItem_withCorrectManufacturer() {
        ItemRequestDto dto = new ItemRequestDto(null, "Pixel 9", 899, 200, 1L);
        ItemResponseDto saved = itemService.create(dto);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getManufacturer().getName()).isEqualTo("United States");
    }

    @Test
    @Transactional
    void shouldUpdateItem_changeManufacturer() {
        ItemResponseDto existing = itemService.findById(1L);
        ItemRequestDto updateDto = new ItemRequestDto(existing.getId(), "iPhone 15 Pro", 1199, 150, 3L);
        ItemResponseDto updated = itemService.update(existing.getId(), updateDto);
        assertThat(updated.getPrice()).isEqualTo(1199);
        assertThat(updated.getManufacturer().getName()).isEqualTo("Germany");
    }

    @Test
    @Transactional
    void shouldDeleteItem() {
        long initialSize = itemService.findAll().size();
        itemService.deleteById(1L);
        assertThat(itemService.findAll()).hasSize((int) initialSize - 1);
    }
}