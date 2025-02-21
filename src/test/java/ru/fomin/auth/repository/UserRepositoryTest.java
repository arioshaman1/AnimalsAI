package ru.fomin.auth.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.fomin.auth.security.model.User;
import ru.fomin.auth.security.repository.UserRepository;
import ru.fomin.auth.util.DataUtil;

import java.util.List;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Test save user functionality")
    public void givenUserToSave_whenSaveUser_thenUserIsSaved() {
        //given
        User user = DataUtil.getUserDanil();
        //when
        User savedUser = userRepository.save(user);
        //
        Assertions.assertNotNull(savedUser);
        Assertions.assertNotNull(savedUser.getId());
        Assertions.assertNotNull(savedUser.getEmail());
        Assertions.assertNotNull(savedUser.getUsername());
        Assertions.assertNotNull(savedUser.getPassword());
        Assertions.assertNotNull(savedUser.getRole());
        Assertions.assertNotNull(savedUser.getStatus());
    }

    @Test
    @DisplayName("Test update user functionality")
    public void givenSavedUser_whenUpdateUser_thenUserIsUpdated() {
        //given
        String updatedUsername = "updatedUsername";
        User UserToSave = DataUtil.getUserDanil();
        userRepository.save(UserToSave);
        //when
        User userToUpdate = userRepository.findById(UserToSave.getId()).
                orElse(null);
        userToUpdate.setUsername(updatedUsername);
        userRepository.save(userToUpdate);
        //then
        Assertions.assertNotNull(userToUpdate);
        Assertions.assertNotNull(userToUpdate.getId());
        Assertions.assertNotNull(userToUpdate.getEmail());
        Assertions.assertNotNull(userToUpdate.getUsername());
        Assertions.assertEquals(userToUpdate.getUsername(), updatedUsername);
        Assertions.assertNotNull(userToUpdate.getPassword());
        Assertions.assertNotNull(userToUpdate.getRole());
        Assertions.assertNotNull(userToUpdate.getStatus());
    }

    @Test
    @DisplayName("Test find by id user functionality")
    public void givenSavedUser_whenFindById_thenUserIsReturned() {
        //given
        User userToSave = DataUtil.getUserDanil();
        userRepository.save(userToSave);
        //when
        User userToFound = userRepository.findById(userToSave.getId())
                .orElse(null);
        //then
        Assertions.assertNotNull(userToFound);
        Assertions.assertNotNull(userToFound.getId());
        Assertions.assertNotNull(userToFound.getEmail());
        Assertions.assertNotNull(userToFound.getUsername());
        Assertions.assertNotNull(userToFound.getPassword());
        Assertions.assertNotNull(userToFound.getRole());
        Assertions.assertNotNull(userToFound.getStatus());
    }

    @Test
    @DisplayName("Test find by id user functionality when user not found")
    public void givenNotSavedUser_whenFIndById_thenUserIsNotReturned() {
        //given
        //when
        User userToFound = userRepository.findById(-1L)
                .orElse(null);
        //then
        Assertions.assertNull(userToFound);
    }

    @Test
    @DisplayName("Test find by email user functionality")
    public void givenSavedUser_whenFindByEmail_thenUserIsReturned() {
        //given
        User userToSave = DataUtil.getUserDanil();
        userRepository.save(userToSave);
        //when
        User userToFound = userRepository.findByEmail(userToSave.getEmail())
                .orElse(null);
        //then
        Assertions.assertNotNull(userToFound);
        Assertions.assertNotNull(userToFound.getId());
        Assertions.assertNotNull(userToFound.getEmail());
        Assertions.assertNotNull(userToFound.getUsername());
        Assertions.assertNotNull(userToFound.getPassword());
        Assertions.assertNotNull(userToFound.getRole());
        Assertions.assertNotNull(userToFound.getStatus());
    }

    @Test
    @DisplayName("Test find by email user functionality when user not found")
    public void givenNotSavedUser_whenFindByEmail_thenUserIsNotReturned() {
        //given
        //when
        User userToFound = userRepository.findByEmail("notExistingEmail@mail.com")
                .orElse(null);
        //then
        Assertions.assertNull(userToFound);
    }

    @Test
    @DisplayName("Test find all functionality")
    public void givenThreeSavedUsers_whenFindAll_thenUsersIsReturned() {
        //given
        User userToSave1 = DataUtil.getUserDanil();
        User userToSave2 = DataUtil.getUserOleg();
        User userToSave3 = DataUtil.getUserDima();
        List<User> users = List.of(userToSave1, userToSave2, userToSave3);
        userRepository.saveAll(users);
        //when
        List<User> usersToFound = userRepository.findAll();
        //then
        Assertions.assertNotNull(usersToFound);
        Assertions.assertEquals(usersToFound.size(), 3);
        Assertions.assertEquals(users, usersToFound);
    }

    @Test
    @DisplayName("Test delete user functionality")
    public void givenSavedUser_whenDeleteUser_thenUserIsDeleted() {
        //given
        User userToSave = DataUtil.getUserDanil();
        userRepository.save(userToSave);
        //when
        userRepository.deleteById(userToSave.getId());
        //then
        User userToFound = userRepository.findById(userToSave.getId())
                .orElse(null);
        //then
        Assertions.assertNull(userToFound);
    }

}
