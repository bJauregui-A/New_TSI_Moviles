    package com.example.new_tsi_moviles.dto;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class CursoDTO {

        private Long id;
        private String nombre;
        private String descripcion;
        private Integer precio;
        private Integer horas;
        private String dirigidoa;
        private String modalidad;
        private String linkPago;
        private Boolean activo;

    }
