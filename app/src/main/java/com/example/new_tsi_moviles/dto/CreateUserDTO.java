package com.example.new_tsi_moviles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

    private String email;
    private String nombre;
    private String rut;
    private String apellido;
    private String password;
}