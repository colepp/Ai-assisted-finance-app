package colepp.app.wealthwisebackend.users.mappers;


import colepp.app.wealthwisebackend.users.dtos.RegisterUserDto;
import colepp.app.wealthwisebackend.users.dtos.UpdateUserDto;
import colepp.app.wealthwisebackend.users.dtos.UserDto;
import colepp.app.wealthwisebackend.users.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
    User registerUserDtoToUser(RegisterUserDto registerUserDto);
    void updateUser(UpdateUserDto userUpdateRequest,@MappingTarget User user);
}
