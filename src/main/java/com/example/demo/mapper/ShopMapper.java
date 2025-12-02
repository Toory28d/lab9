package com.example.demo.mapper;

import com.example.demo.dto.CountryDto;
import com.example.demo.dto.ItemDto;
import com.example.demo.models.Country;
import com.example.demo.models.Item;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ShopMapper {

    CountryDto toDto(Country country);
    Country toEntity(CountryDto dto);

    ItemDto toDto(Item item);
    Item toEntity(ItemDto dto);

    void updateFromDto(CountryDto dto, @MappingTarget Country country);
    void updateFromDto(ItemDto dto, @MappingTarget Item item);
}