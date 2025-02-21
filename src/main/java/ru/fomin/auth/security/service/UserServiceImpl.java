package ru.fomin.auth.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fomin.auth.security.exception.UserNotFoundException;
import ru.fomin.auth.security.mapper.UserMapper;
import ru.fomin.auth.security.model.User;
import ru.fomin.auth.security.repository.UserRepository;
import ru.fomin.auth.security.rest.model.UserRequest;
import ru.fomin.auth.security.rest.model.UserResponse;

import java.util.List;
import java.util.Objects;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        return userMapper.map(userRepository.save(userMapper.map(userRequest)));
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        boolean isExist = userRepository.existsById(userMapper.map(userRequest).getId());
        if (!isExist){
            throw new UserNotFoundException("Order not found");
        }
        return userMapper.map(userRepository.save(userMapper.map(userRequest)));
    }

    @Override
    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Order not found"));
        return userMapper.map(user);
    }

    @Override
    public UserResponse findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Order not found"));
        return userMapper.map(user);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        return userMapper.map(userRepository.findAll());
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Order not found"));
        userRepository.deleteById(id);
    }
}
