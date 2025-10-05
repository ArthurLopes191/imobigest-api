-- Adiciona referência de imobiliária na tabela PROFISSIONAL
ALTER TABLE PROFISSIONAL ADD COLUMN id_imobiliaria INT;
ALTER TABLE PROFISSIONAL ADD CONSTRAINT fk_profissional_imobiliaria
    FOREIGN KEY (id_imobiliaria) REFERENCES IMOBILIARIA(id);

-- Adiciona referência de imobiliária na tabela VENDA
ALTER TABLE VENDA ADD COLUMN id_imobiliaria INT;
ALTER TABLE VENDA ADD CONSTRAINT fk_venda_imobiliaria
    FOREIGN KEY (id_imobiliaria) REFERENCES IMOBILIARIA(id);