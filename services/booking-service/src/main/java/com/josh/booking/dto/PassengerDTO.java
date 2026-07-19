package com.josh.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {

    @NotBlank(message = "Passenger name is required")
    private String name;

    @NotBlank(message = "Passenger email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Passenger age is required")
    @Min(value = 0, message = "Age cannot be negative")
    private Integer age;

    @NotBlank(message = "Passenger gender is required")
    private String gender;

    @NotBlank(message = "Assigned seat is required")
    private String assignedSeat;
}
