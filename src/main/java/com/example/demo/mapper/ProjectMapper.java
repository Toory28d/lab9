package com.example.demo.mapper;

import com.example.demo.dto.ProjectDTO;
import com.example.demo.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, TaskMapper.class})
public interface ProjectMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "categories", source = "categories")
    @Mapping(target = "tasks", source = "tasks")
    ProjectDTO toDto(Project entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "categories", source = "categories")
    @Mapping(target = "tasks", source = "tasks")
    Project toEntity(ProjectDTO dto);

    List<ProjectDTO> toDtos(List<Project> entities);
}