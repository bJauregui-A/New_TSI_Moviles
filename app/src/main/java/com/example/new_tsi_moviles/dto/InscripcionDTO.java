package com.example.new_tsi_moviles.dto;

import com.example.new_tsi_moviles.model.CursoUserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionDTO {

    @NotNull
    private Long idUser;
    @NotNull
    private Long idCurso;


    private CursoUserState estado;
}
