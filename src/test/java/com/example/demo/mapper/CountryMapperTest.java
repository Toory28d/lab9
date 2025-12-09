package com.example.demo.mapper;

import com.example.demo.dto.CountryDto;
import com.example.demo.entity.Country;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CountryMapperTest {

    private final CountryMapper mapper = Mappers.getMapper(CountryMapper.class);

    @Test
    void entityToDto() {
        Country entity = new Country(1L, "USA", "USA",null);
        CountryDto dto = mapper.toDto(entity);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("USA");
        assertThat(dto.getCode()).isEqualTo("USA");
    }

    @Test
    void dtoToEntity() {
        CountryDto dto = new CountryDto(1L, "USA", "USA");
        Country entity = mapper.toEntity(dto);
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getName()).isEqualTo("USA");
        assertThat(entity.getCode()).isEqualTo("USA");
    }

    @Test
    void listConversion() {
        List<Country> entities = List.of(
                new Country(1L, "USA", "USA",null),
                new Country(2L, "China", "CHN",null)
        );
        List<CountryDto> dtos = mapper.toDtoList(entities);
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getName()).isEqualTo("USA");
    }
}