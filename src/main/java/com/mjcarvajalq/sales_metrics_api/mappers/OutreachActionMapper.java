package com.mjcarvajalq.sales_metrics_api.mappers;

import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionRequest;
import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionResponse;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDetailResponse;
import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;
import com.mjcarvajalq.sales_metrics_api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OutreachActionMapper {

    @Mapping(target = "userId", source = "user.id")
    OutreachActionDTO toDTO(OutreachAction entity);

    List<OutreachActionDTO> toDTOList(List<OutreachAction> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "dateTime", source = "request.dateTime")
    OutreachAction toEntity(CreateOutreachActionRequest request, User user);


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "type", expression = "java(entity.getType().name())")
    @Mapping(target = "dateTime", source = "entity.dateTime")
    OutreachActionDetailResponse mapToDetailResponse(OutreachAction entity);


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "dateTime", source = "entity.dateTime")
    @Mapping(target = "message", constant = "Outreach action created successfully")
    CreateOutreachActionResponse mapToCreateResponse(OutreachAction entity);

}

