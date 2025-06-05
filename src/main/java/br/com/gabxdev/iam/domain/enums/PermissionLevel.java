package br.com.gabxdev.iam.domain.enums;

import lombok.Getter;

@Getter
public enum PermissionLevel {
    WRITE(1),
    READ(2),
    ADMIN(3);

    private final Integer value;

    PermissionLevel(Integer value) {
        this.value = value;
    }
}
