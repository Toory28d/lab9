package com.example.demo.mapper;

import com.example.demo.dto.ItemRequestDto;
import com.example.demo.dto.ItemResponseDto;
import com.example.demo.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CountryMapper.class)
public interface ItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "manufacturer", ignore = true)
    Item toEntity(ItemRequestDto dto);

    ItemResponseDto toResponseDto(Item entity);

    List<ItemResponseDto> toResponseDtoList(List<Item> entities);
}