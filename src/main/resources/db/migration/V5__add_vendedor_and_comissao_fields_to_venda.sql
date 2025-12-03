-- V5__add_vendedor_and_comissao_fields_to_venda.sql

-- Adiciona campos do vendedor
ALTER TABLE VENDA ADD COLUMN vendedor_nome VARCHAR(255);
ALTER TABLE VENDA ADD COLUMN vendedor_contato VARCHAR(100);

-- Adiciona campos de comissão (porcentagens)
ALTER TABLE VENDA ADD COLUMN comissao_comprador DECIMAL(5,2);
ALTER TABLE VENDA ADD COLUMN comissao_vendedor DECIMAL(5,2);
ALTER TABLE VENDA ADD COLUMN comissao_imobiliaria DECIMAL(5,2);

-- Adiciona campo para valor monetário da comissão
ALTER TABLE VENDA ADD COLUMN valor_comissao_imobiliaria DECIMAL(15,2);

-- Adiciona constraints para garantir que as porcentagens sejam válidas (0-100%)
ALTER TABLE VENDA ADD CONSTRAINT chk_comissao_comprador CHECK (comissao_comprador >= 0 AND comissao_comprador <= 100);
ALTER TABLE VENDA ADD CONSTRAINT chk_comissao_vendedor CHECK (comissao_vendedor >= 0 AND comissao_vendedor <= 100);
ALTER TABLE VENDA ADD CONSTRAINT chk_comissao_imobiliaria CHECK (comissao_imobiliaria >= 0 AND comissao_imobiliaria <= 100);