package ru.otus.otusx.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(cacheStrategy = EqualsAndHashCode.CacheStrategy.LAZY, onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private UUID uuid;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String sex;
    private String interests;
    private String city;
    private String password;
}
