-- Adiciona campo para comissão automática na tabela CARGO
ALTER TABLE CARGO ADD COLUMN comissao_automatica BOOLEAN DEFAULT false;

-- Atualiza registros existentes para false (opcional, já que o DEFAULT já faz isso)
UPDATE CARGO SET comissao_automatica = false WHERE comissao_automatica IS NULL;