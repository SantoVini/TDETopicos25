-- Inserir autores
INSERT INTO autores (nome, email, datanascimento, biografia) VALUES
                                                                  ('José de Alencar', 'alencar@literatura.com', '1829-05-01', 'Escritor brasileiro, representante do Romantismo'),
                                                                  ('Machado de Assis', 'machado@academia.com', '1839-06-21', 'Fundador da Academia Brasileira de Letras'),
                                                                  ('Clarice Lispector', 'clarice@contos.com', '1920-12-10', 'Uma das mais importantes escritoras do século XX');

-- Inserir livros
INSERT INTO livros (titulo, isbn, datapublicacao, numeropaginas, disponivel, autor_id) VALUES
                                                                                             ('O Guarani', '123-4567890123', '1857-01-01', 320, true, 1),
                                                                                             ('Dom Casmurro', '123-4567890125', '1899-01-01', 256, false, 2),
                                                                                             ('A Hora da Estrela', '123-4567890127', '1977-01-01', 96, true, 3);

-- Inserir empréstimos
INSERT INTO emprestimos (nomeusuario, emailusuario, dataemprestimo, datadevolucaoprevista, livro_id) VALUES
                                                                                                              ('João Santos', 'joao@email.com', '2024-11-10', '2024-11-24', 2),
                                                                                                              ('Ana Costa', 'ana@email.com', '2024-11-15', '2024-11-29', 3);