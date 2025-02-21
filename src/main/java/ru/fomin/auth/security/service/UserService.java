package ru.fomin.auth.security.service;


import ru.fomin.auth.security.rest.model.UserRequest;
import ru.fomin.auth.security.rest.model.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse saveUser(UserRequest userRequest);

    UserResponse updateUser(UserRequest userRequest);

    UserResponse findUserById(Long id);

    UserResponse findUserByEmail(String email);

    List<UserResponse> findAllUsers();

    void deleteUserById(Long id);

}
