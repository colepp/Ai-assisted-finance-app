package colepp.app.wealthwisebackend.users.mappers;


import colepp.app.wealthwisebackend.users.dtos.RegisterUserRequest;
import colepp.app.wealthwisebackend.users.dtos.UpdateUserRequest;
import colepp.app.wealthwisebackend.users.dtos.UserDto;
import colepp.app.wealthwisebackend.users.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
    User registerUserDtoToUser(RegisterUserRequest registerUserDto);
    void updateUser(UpdateUserRequest userUpdateRequest, @MappingTarget User user);
}
