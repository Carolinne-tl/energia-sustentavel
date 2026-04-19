-- V2__Insert_Initial_Data.sql

-- Inserir usuário admin (senha: admin123)
INSERT INTO usuarios (username, password, email, role, ativo)
VALUES ('admin', '$2a$10$XMqZ7qZ7qZ7qZ7qZ7qZ7qeJ7qZ7qZ7qZ7qZ7qZ7qZ7qZ7qZ7qZ7q', 'admin@energiasustentavel.com', 'ADMIN', 1);

-- Inserir usuário comum (senha: user123)
INSERT INTO usuarios (username, password, email, role, ativo)
VALUES ('usuario', '$2a$10$XMqZ7qZ7qZ7qZ7qZ7qZ7qeJ7qZ7qZ7qZ7qZ7qZ7qZ7qZ7qZ7qZ7q', 'usuario@energiasustentavel.com', 'USER', 1);

-- Inserir empresas de exemplo
INSERT INTO empresas (nome, cnpj, setor, endereco, cidade, estado, contato, email)
VALUES ('TechGreen Soluções', '12.345.678/0001-90', 'Tecnologia', 'Av. Paulista, 1000', 'São Paulo', 'SP', '(11) 98765-4321', 'contato@techgreen.com.br');

INSERT INTO empresas (nome, cnpj, setor, endereco, cidade, estado, contato, email)
VALUES ('EcoIndustria LTDA', '98.765.432/0001-10', 'Industrial', 'Rua das Flores, 500', 'Curitiba', 'PR', '(41) 99999-8888', 'contato@ecoindustria.com.br');

INSERT INTO empresas (nome, cnpj, setor, endereco, cidade, estado, contato, email)
VALUES ('Comércio Sustentável SA', '11.222.333/0001-44', 'Comércio', 'Rua do Comércio, 200', 'Rio de Janeiro', 'RJ', '(21) 91234-5678', 'contato@comerciosustentavel.com.br');

-- Inserir equipamentos
INSERT INTO equipamentos (empresa_id, nome, tipo, potencia_watts, localizacao, status, data_instalacao)
VALUES (1, 'Ar Condicionado Central - Andar 1', 'AR_CONDICIONADO', 5000.00, 'Andar 1 - Área Administrativa', 'ATIVO', TO_DATE('2023-01-15', 'YYYY-MM-DD'));

INSERT INTO equipamentos (empresa_id, nome, tipo, potencia_watts, localizacao, status, data_instalacao)
VALUES (1, 'Sistema de Iluminação LED - Escritório', 'ILUMINACAO', 2000.00, 'Escritório Principal', 'ATIVO', TO_DATE('2023-03-20', 'YYYY-MM-DD'));

INSERT INTO equipamentos (empresa_id, nome, tipo, potencia_watts, localizacao, status, data_instalacao)
VALUES (1, 'Servidores TI', 'SERVIDOR', 3500.00, 'Sala de Servidores', 'ATIVO', TO_DATE('2022-11-10', 'YYYY-MM-DD'));

INSERT INTO equipamentos (empresa_id, nome, tipo, potencia_watts, localizacao, status, data_instalacao)
VALUES (2, 'Linha de Produção A', 'MAQUINARIO', 15000.00, 'Galpão 1', 'ATIVO', TO_DATE('2022-06-01', 'YYYY-MM-DD'));

INSERT INTO equipamentos (empresa_id, nome, tipo, potencia_watts, localizacao, status, data_instalacao)
VALUES (2, 'Ar Condicionado Industrial', 'AR_CONDICIONADO', 8000.00, 'Galpão 2', 'ATIVO', TO_DATE('2023-02-14', 'YYYY-MM-DD'));

INSERT INTO equipamentos (empresa_id, nome, tipo, potencia_watts, localizacao, status, data_instalacao)
VALUES (3, 'Sistema de Refrigeração', 'REFRIGERACAO', 6000.00, 'Área de Estoque', 'ATIVO', TO_DATE('2023-04-10', 'YYYY-MM-DD'));

-- Inserir dados de consumo
INSERT INTO consumo_energia (empresa_id, equipamento_id, data_leitura, consumo_kwh, custo_estimado, periodo)
VALUES (1, 1, SYSTIMESTAMP - 30, 450.50, 315.35, 'DIARIO');

INSERT INTO consumo_energia (empresa_id, equipamento_id, data_leitura, consumo_kwh, custo_estimado, periodo)
VALUES (1, 2, SYSTIMESTAMP - 30, 180.20, 126.14, 'DIARIO');

INSERT INTO consumo_energia (empresa_id, equipamento_id, data_leitura, consumo_kwh, custo_estimado, periodo)
VALUES (1, 3, SYSTIMESTAMP - 30, 520.00, 364.00, 'DIARIO');

INSERT INTO consumo_energia (empresa_id, data_leitura, consumo_kwh, custo_estimado, periodo)
VALUES (1, SYSTIMESTAMP - 7, 8500.00, 5950.00, 'SEMANAL');

INSERT INTO consumo_energia (empresa_id, data_leitura, consumo_kwh, custo_estimado, periodo)
VALUES (2, SYSTIMESTAMP - 7, 25000.00, 17500.00, 'SEMANAL');

-- Inserir metas de eficiência
INSERT INTO metas_eficiencia (empresa_id, descricao, tipo_meta, valor_alvo, valor_atual, unidade, data_inicio, data_fim, status)
VALUES (1, 'Reduzir consumo de energia em 15%', 'REDUCAO_CONSUMO', 15.00, 8.50, 'PERCENTUAL', TO_DATE('2025-01-01', 'YYYY-MM-DD'), TO_DATE('2025-12-31', 'YYYY-MM-DD'), 'EM_ANDAMENTO');

INSERT INTO metas_eficiencia (empresa_id, descricao, tipo_meta, valor_alvo, valor_atual, unidade, data_inicio, data_fim, status)
VALUES (2, 'Implementar energia solar para 30% do consumo', 'ENERGIA_RENOVAVEL', 30.00, 12.00, 'PERCENTUAL', TO_DATE('2025-03-01', 'YYYY-MM-DD'), TO_DATE('2026-02-28', 'YYYY-MM-DD'), 'EM_ANDAMENTO');

INSERT INTO metas_eficiencia (empresa_id, descricao, tipo_meta, valor_alvo, valor_atual, unidade, data_inicio, data_fim, status)
VALUES (3, 'Reduzir custos com energia em R$ 50.000', 'REDUCAO_CUSTO', 50000.00, 18000.00, 'BRL', TO_DATE('2025-01-01', 'YYYY-MM-DD'), TO_DATE('2025-12-31', 'YYYY-MM-DD'), 'EM_ANDAMENTO');

COMMIT;
