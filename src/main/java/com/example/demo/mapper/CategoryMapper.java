package com.example.demo.mapper;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id",source = "id")
    @Mapping(target = "name",source = "name")
    @Mapping(target ="description",source="description")
    CategoryDTO toDto(Category entity);

    @Mapping(target = "id",source = "id")
    @Mapping(target = "name",source = "name")
    @Mapping(target ="description",source="description")
    @Mapping(target = "projects",ignore = true )
    Category toEntity(CategoryDTO dto);

    List<CategoryDTO> toDtos(List<Category> entities);
}
