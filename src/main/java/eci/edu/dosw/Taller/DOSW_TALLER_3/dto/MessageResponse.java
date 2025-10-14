package eci.edu.dosw.Taller.DOSW_TALLER_3.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para mensajes simples de éxito
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MessageResponse {
    private String message;
    private String timestamp;
}