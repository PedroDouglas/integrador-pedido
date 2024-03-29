CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    quantidade_disponivel INT NOT NULL,
    valor_unitario DECIMAL(10, 2) NOT NULL
);