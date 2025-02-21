package ru.fomin.auth.security.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of(Permission.WRITE,Permission.READ)),
    USER(Set.of(Permission.READ));

    private final Set<Permission> setPermission;

    Role(Set<Permission> setPermission) {
        this.setPermission = setPermission;
    }

    public Set<Permission> getPermission() {
        return setPermission;
    }

    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthority() {
        return getPermission().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
