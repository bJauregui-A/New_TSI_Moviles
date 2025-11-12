package com.example.new_tsi_moviles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JokeDTO {
    private String type;
    private String setup;
    private String punchline;
    private Integer id;
}
