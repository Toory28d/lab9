package com.example.demo.mapper;

import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "details", source = "details")
    @Mapping(target= "status",source = "status")
    @Mapping(target = "projectId", source = "project.id")
    TaskDTO toDto(Task entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "details", source = "details")
    @Mapping(target= "status",source = "status")
    @Mapping(target = "project", ignore=true)
    Task toEntity(TaskDTO dto);

    List<TaskDTO> toDtos(List<Task> entities);
}
