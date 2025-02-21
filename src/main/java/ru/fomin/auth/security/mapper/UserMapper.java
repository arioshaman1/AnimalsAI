package ru.fomin.auth.security.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.fomin.auth.security.model.User;
import ru.fomin.auth.security.rest.model.UserRequest;
import ru.fomin.auth.security.rest.model.UserResponse;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
    UserResponse map(User user);
    List<UserResponse> map(List<User> users);
    User map(UserRequest userRequest);
}
