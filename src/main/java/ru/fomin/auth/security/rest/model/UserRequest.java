package ru.fomin.auth.security.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.fomin.auth.security.model.Role;
import ru.fomin.auth.security.model.Status;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "username")
    private String username;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @JsonProperty(value = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
