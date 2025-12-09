package com.example.demo.mapper;

import com.example.demo.dto.ItemRequestDto;
import com.example.demo.dto.ItemResponseDto;
import com.example.demo.entity.Country;
import com.example.demo.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ItemMapperTest {

    private ItemMapper mapper;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        ItemMapperImpl itemMapperImpl = new ItemMapperImpl();
        Field field = ItemMapperImpl.class.getDeclaredField("countryMapper");
        field.setAccessible(true);
        field.set(itemMapperImpl, Mappers.getMapper(CountryMapper.class));
        mapper = itemMapperImpl;
    }

    @Test
    void requestDtoToEntity_ignoreIdAndManufacturer() {
        ItemRequestDto dto = new ItemRequestDto(99L, "Phone", 1000, 50, 1L);
        Item entity = mapper.toEntity(dto);
        assertThat(entity.getId()).isNull();
        assertThat(entity.getName()).isEqualTo("Phone");
        assertThat(entity.getPrice()).isEqualTo(1000);
        assertThat(entity.getQuantity()).isEqualTo(50);
        assertThat(entity.getManufacturer()).isNull();
    }

    @Test
    void entityToResponseDto_withNestedCountry() {
        Country country = new Country(1L, "USA", "USA",null);
        Item entity = new Item();
        entity.setId(5L);
        entity.setName("iPhone");
        entity.setPrice(999);
        entity.setQuantity(100);
        entity.setManufacturer(country);

        ItemResponseDto dto = mapper.toResponseDto(entity);
        assertThat(dto.getId()).isEqualTo(5L);
        assertThat(dto.getName()).isEqualTo("iPhone");
        assertThat(dto.getManufacturer().getName()).isEqualTo("USA");
    }
}