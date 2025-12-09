package com.example.demo.mapper;

import com.example.demo.dto.CountryDto;
import com.example.demo.entity.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDto toDto(Country entity);
    Country toEntity(CountryDto dto);
    List<CountryDto> toDtoList(List<Country> entities);
}