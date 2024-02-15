package ru.otus.otusx.logic.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class UserRegistrationRequest {
    @NotBlank(message = "Необходимо указать имя")
    @Size(max = 64, message = "Слишком длинное имя")
    @JsonProperty("first_name")
    private String name;
    @NotBlank(message = "Необходимо указать фамилию")
    @Size(max = 64, message = "Слишком длинная фамилия")
    @JsonProperty("second_name")
    private String surname;
    @JsonProperty("birthdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    @JsonProperty("biography")
    @Size(max = 1024*1024, message = "Слишком длинная биография")
    private String interests;
    @Size(max = 64, message = "Слишком длинное название города")
    private String city;
    @NotBlank
    @Size(min = 5, message = "Слишком короткий пароль")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$",
            message = "Пароль должен содержать хотя бы одну букву и одну цифру")
    private String password;
}
