package ru.otus.otusx.logic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class UserDto {

    private UUID uuid;
    @JsonProperty("first_name")
    private String name;
    @JsonProperty("second_name")
    private String surname;
    @JsonProperty("birthdate")
    private LocalDate birthDate;
    private String sex;
    @JsonProperty("biography")
    private String interests;
    private String city;
}
