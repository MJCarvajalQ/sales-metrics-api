package com.mjcarvajalq.sales_metrics_api.mappers;
import com.mjcarvajalq.sales_metrics_api.model.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    default Long mapUserId(User user){
        return user != null ? user.getId() : null;
    }

    default String mapUserName(User user){
        return user != null ? user.getName() : null;
    }
}
