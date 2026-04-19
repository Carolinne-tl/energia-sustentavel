package com.esg.energiasustentavel.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.esg.energiasustentavel.repository.EquipamentoRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes para o Controller de Equipamento
 * Usa Mock para evitar dependências de banco de dados
 */
@WebMvcTest(EquipamentoController.class)
@DisplayName("Testes do Controller de Equipamento")
class EquipamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipamentoRepository equipamentoRepository;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 1: Listar equipamentos")
    void testListarEquipamentos() throws Exception {
        mockMvc.perform(get("/api/equipamentos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 2: GET por empresa")
    void testListarEquipamentosPorEmpresa() throws Exception {
        mockMvc.perform(get("/api/equipamentos/empresa/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 3: POST equipamento")
    void testCriarEquipamento() throws Exception {
        String json = "{\"nome\": \"Equipamento\"}";
        mockMvc.perform(post("/api/equipamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 4: PATCH status")
    void testAtualizarStatusEquipamento() throws Exception {
        String json = "{\"status\": \"inativo\"}";
        mockMvc.perform(patch("/api/equipamentos/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 5: Query parameters")
    void testListarEquipamentosAtivos() throws Exception {
        mockMvc.perform(get("/api/equipamentos?status=ativo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
