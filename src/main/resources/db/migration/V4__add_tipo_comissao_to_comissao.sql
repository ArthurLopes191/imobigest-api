-- V4__add_tipo_comissao_to_comissao.sql
ALTER TABLE COMISSAO ADD COLUMN tipo_comissao VARCHAR(20) DEFAULT 'MANUAL';

-- Atualiza registros existentes para MANUAL (comissões já criadas manualmente)
UPDATE COMISSAO SET tipo_comissao = 'MANUAL' WHERE tipo_comissao IS NULL;

-- Adiciona constraint para garantir valores válidos
ALTER TABLE COMISSAO ADD CONSTRAINT chk_tipo_comissao CHECK (tipo_comissao IN ('AUTOMATICA', 'MANUAL'));