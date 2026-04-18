-- ==========================================
-- SCRIPT DE CARGA INICIAL - SISTEMA MUTTLEY
-- ==========================================

-- 1. Inserindo Usuários (Admin e Gestores)
INSERT INTO usuarios (aprovado, email, nome, senha, perfil) VALUES 
(1, 'luccas.corsino@fatec.sp.gov.br', 'Luccas Corsino', '123456', 'ADMIN'),
(1, 'gestor.ativo@muttley.com', 'Ana Souza', '123', 'GESTOR'),
(0, 'gestor.pendente@muttley.com', 'Carlos Silva', '123', 'GESTOR');

-- 2. Inserindo Apresentadores
INSERT INTO apresentadores (cpf, nome, telefone) VALUES 
('111.111.111-11', 'Prof. Roberto (Especialista Java)', '(11) 99999-1111'),
('222.222.222-22', 'Marcos (Arquiteto Next.js)', '(11) 99999-2222'),
('333.333.333-33', 'Fernanda (Engenheira AWS)', '(11) 99999-3333');

-- 3. Inserindo Eventos
INSERT INTO eventos (data_fim, data_inicio, hora_fim, hora_inicio, titulo, descricao) VALUES 
('2026-05-10', '2026-05-10', '16:00:00', '14:00:00', 'Workshop de Spring Boot', 'Aprofundando em APIs REST e Clean Architecture.'),
('2026-05-15', '2026-05-15', '21:00:00', '19:00:00', 'Masterclass Front-end', 'Como criar interfaces modernas usando Next.js e Tailwind CSS.'),
('2026-05-20', '2026-05-20', '12:00:00', '09:00:00', 'Maratona de Algoritmos', 'Desafios focados em estruturas de dados, grafos e árvores binárias.');

-- 4. Inserindo Participantes
INSERT INTO participantes (email, nome) VALUES 
('participante1@fatec.sp.gov.br', 'João Pedro'),
('participante2@fatec.sp.gov.br', 'Mariana Costa'),
('participante3@fatec.sp.gov.br', 'Pedro Henrique');