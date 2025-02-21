package ru.fomin.auth.util;

import ru.fomin.auth.security.model.Role;
import ru.fomin.auth.security.model.Status;
import ru.fomin.auth.security.model.User;
import ru.fomin.auth.security.rest.model.UserRequest;
import ru.fomin.auth.security.rest.model.UserResponse;


public class DataUtil {

    public static User getUserDanil(){
        return new User()
                .setEmail("danil@mail.com")
                .setUsername("danil")
                .setPassword("danil")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }
    public static User getUserOleg(){
        return new User()
                .setEmail("oleg@mail.com")
                .setUsername("oleg")
                .setPassword("oleg")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }
    public static User getUserDima(){
        return new User()
                .setEmail("dima@mail.com")
                .setUsername("dima")
                .setPassword("dima")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }

    public static User getUserDanilWithId(){
        return new User()
                .setId(1L)
                .setEmail("danil@mail.com")
                .setUsername("danil")
                .setPassword("danil")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }
    public static User getUserOlegWithId(){
        return new User()
                .setId(2L)
                .setEmail("oleg@mail.com")
                .setUsername("oleg")
                .setPassword("oleg")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }
    public static User getUserDimaWithId(){
        return new User()
                .setId(3L)
                .setEmail("dima@mail.com")
                .setUsername("dima")
                .setPassword("dima")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }

    public static UserResponse getUserResponseDanil(){
        return new UserResponse()
                .setId(1L)
                .setEmail("danil@mail.com")
                .setUsername("danil")
                .setPassword("danil")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }
    public static UserResponse getUserResponseOleg(){
        return new UserResponse()
                .setId(2L)
                .setEmail("oleg@mail.com")
                .setUsername("oleg")
                .setPassword("oleg")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }
    public static UserResponse getUserResponseDima(){
        return new UserResponse()
                .setId(3L)
                .setEmail("dima@mail.com")
                .setUsername("dima")
                .setPassword("dima")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }

    public static UserRequest getUserRequestDanil(){
        return new UserRequest()
                .setEmail("danil@mail.com")
                .setUsername("danil")
                .setPassword("danil")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }
    public static UserRequest getUserRequestOleg(){
        return new UserRequest()
                .setEmail("oleg@mail.com")
                .setUsername("oleg")
                .setPassword("oleg")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }
    public static UserRequest getUserRequestDima(){
        return new UserRequest()
                .setEmail("dima@mail.com")
                .setUsername("dima")
                .setPassword("dima")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }

    public static UserRequest getUserRequestDanilWithId(){
        return new UserRequest()
                .setId(1L)
                .setEmail("danil@mail.com")
                .setUsername("danil")
                .setPassword("danil")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }
    public static UserRequest getUserRequestOlegWithId(){
        return new UserRequest()
                .setId(2L)
                .setEmail("oleg@mail.com")
                .setUsername("oleg")
                .setPassword("oleg")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }
    public static UserRequest getUserRequestDimaWithId(){
        return new UserRequest()
                .setId(3L)
                .setEmail("dima@mail.com")
                .setUsername("dima")
                .setPassword("dima")
                .setRole(Role.USER)
                .setStatus(Status.ACTIVE);
    }






}
