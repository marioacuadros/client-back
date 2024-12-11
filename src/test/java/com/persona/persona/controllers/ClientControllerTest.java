package com.persona.persona.controllers;
import com.persona.persona.models.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetClient_Success() throws Exception {
        mockMvc.perform(get("/api/v1/client")
                        .param("docType", "C")
                        .param("docNumber", "23445322")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Cliente encontrado."))
                .andExpect(jsonPath("$.data.documentType").value("C"))
                .andExpect(jsonPath("$.data.documentNumber").value("23445322"))
                .andExpect(jsonPath("$.data.firstName").value("Pedro"))
                .andExpect(jsonPath("$.data.secondName").value("Pablo"))
                .andExpect(jsonPath("$.data.firstLastName").value("Perez"))
                .andExpect(jsonPath("$.data.secondLastName").value("Paredez"))
                .andExpect(jsonPath("$.data.phone").value("3001231212"))
                .andExpect(jsonPath("$.data.address").value("Calle 1 # 2-3"))
                .andExpect(jsonPath("$.data.city").value("Bogotá"));
    }

    @Test
    public void testGetClient_BadRequest_MissingParams() throws Exception {
        mockMvc.perform(get("/api/v1/client")
                        .param("docType", "")
                        .param("docNumber", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Tipo y número de documento son obligatorios."));
    }

    @Test
    public void testGetClient_BadRequest_InvalidDocType() throws Exception {
        mockMvc.perform(get("/api/v1/client")
                        .param("docType", "X")
                        .param("docNumber", "23445322")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Tipo de documento inválido. Solo se permiten 'C' y 'P'."));
    }

    @Test
    public void testGetClient_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/client")
                        .param("docType", "C")
                        .param("docNumber", "99999999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Cliente no encontrado."));
    }
}
