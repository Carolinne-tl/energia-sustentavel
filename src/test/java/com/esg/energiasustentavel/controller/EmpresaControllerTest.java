package com.esg.energiasustentavel.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.esg.energiasustentavel.model.Empresa;
import com.esg.energiasustentavel.repository.EmpresaRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes para o Controller de Empresa
 * Usa Mock para evitar dependências de banco de dados
 */
@WebMvcTest(EmpresaController.class)
@DisplayName("Testes do Controller de Empresa")
class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaRepository empresaRepository;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 1: Validação de autenticação")
    void testValidacaoAutenticacao() throws Exception {
        mockMvc.perform(get("/api/empresas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 2: Método POST retorna status válido")
    void testMetodoPostValido() throws Exception {
        String json = "{\"nome\": \"Empresa Test\"}";
        mockMvc.perform(post("/api/empresas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 3: Acesso a recurso protegido")
    void testAcessoRecursoProtegido() throws Exception {
        mockMvc.perform(get("/api/empresas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Teste 4: Validação de tipo de conteúdo")
    void testValidacaoTipoConteudo() throws Exception {
        String json = "{\"nome\": \"Empresa\"}";
        mockMvc.perform(post("/api/empresas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}
