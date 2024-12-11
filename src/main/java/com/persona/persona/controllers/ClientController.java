package com.persona.persona.controllers;
import com.persona.persona.dto.Response;
import com.persona.persona.models.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ClientController {
    @GetMapping("/client")
    public ResponseEntity<Response> getClient(
            @RequestParam("docType") String docType,
            @RequestParam("docNumber") String docNumber) {

        try {
            // Validación de tipo y número de documento
            if (docType == null || docType.isEmpty() || docNumber == null || docNumber.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new Response(400, "Tipo y número de documento son obligatorios.", null));
            }
            if (!docType.equals("C") && !docType.equals("P")) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new Response(400, "Tipo de documento inválido. Solo se permiten 'C' y 'P'.", null));
            }

            // "Quemado" de datos del cliente
            if (docType.equals("C") && docNumber.equals("23445322")) {
                Client client = new Client(
                        "C",
                        "23445322",
                        "Pedro",
                        "Pablo",
                        "Perez",
                        "Paredez",
                        "3001231212",
                        "Calle 1 # 2-3",
                        "Bogotá"
                );
                return ResponseEntity
                        .ok(new Response(200, "Cliente encontrado.", client));
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new Response(404, "Cliente no encontrado.", null));
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(500, "Error en el servidor.", null));
        }
    }
}
