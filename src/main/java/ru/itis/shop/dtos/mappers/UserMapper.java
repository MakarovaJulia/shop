package ru.itis.shop.dtos.mappers;

import org.mapstruct.*;
import ru.itis.shop.dtos.UserDto;
import ru.itis.shop.models.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "hashPassword", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    User map(UserDto userDto);

    UserDto map(User user);

    List<UserDto> map(List<User> users);

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "hashPassword", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    void updateUserFromUserDto(UserDto userDto, @MappingTarget User user);
}