package br.com.gabxdev.core.common;

import java.util.List;
import java.util.UUID;

public interface UserIdentity {
    Long getId();

    String getEmail();

    String getFirstName();

    String getLastName();

    List<Role> getRole();

    UUID getAccountId();
}
