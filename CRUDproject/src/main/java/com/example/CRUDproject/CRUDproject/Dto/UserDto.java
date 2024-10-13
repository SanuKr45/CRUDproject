package com.example.CRUDproject.CRUDproject.Dto;

import lombok.*;
import org.hibernate.annotations.processing.Pattern;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String address;

}