package br.com.gabxdev.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    OWNER("OWNER"),
    IAM("IAM");

    private String value;
}
