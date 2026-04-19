package com.esg.energiasustentavel.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.esg.energiasustentavel.repository.ConsumoEnergiaRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes para o Controller de Consumo Energético
 * Usa Mock para evitar dependências de banco de dados
 */
@WebMvcTest(ConsumoEnergiaController.class)
@DisplayName("Testes do Controller de Consumo Energético")
class ConsumoEnergiaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsumoEnergiaRepository consumoRepository;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 1: Registrar consumo")
    void testRegistrarConsumo() throws Exception {
        String json = "{\"equipamentoId\": 1, \"consumoKwh\": 15.5}";
        mockMvc.perform(post("/api/consumo-energia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 2: Listar por equipamento")
    void testListarConsumoPorEquipamento() throws Exception {
        mockMvc.perform(get("/api/consumo-energia/equipamento/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 3: Calcular média")
    void testCalcularMediaConsumoDiario() throws Exception {
        mockMvc.perform(get("/api/consumo-energia/empresa/1/media-diaria")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 4: Validar consumo negativo")
    void testValidarConsumoNegativo() throws Exception {
        String json = "{\"equipamentoId\": 1, \"consumoKwh\": -5.0}";
        mockMvc.perform(post("/api/consumo-energia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 5: Consumo acima do limite")
    void testConsumoAcimaDoLimite() throws Exception {
        mockMvc.perform(get("/api/consumo-energia/empresa/1/acima-limite")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
