CREATE TABLE Usuario (
    ID_Usuario INT PRIMARY KEY,
    CPF VARCHAR(11),
    Email VARCHAR(120),
    Senha VARCHAR(48),
    NomeCompleto VARCHAR(80),
    DataCadastro DATE,
    Telefone VARCHAR(13)
);

CREATE TABLE Abrigo (
    Dono VARCHAR(40),
    Suprimentos VARCHAR(100),
    Capacidade INT,
    ID_abrigo INT PRIMARY KEY,
    DataCadastro DATE,
    Lotacao INT,
    ID_Endereco INT
);

CREATE TABLE Endereco (
    ID_Endereco INT PRIMARY KEY,
    CEP VARCHAR(15),
    Numero INT,
    Bairro VARCHAR(20),
    Latitude VARCHAR(7),
    Longitude VARCHAR(7),
    Cidade VARCHAR(32),
    Complemento VARCHAR(10),
    Rua VARCHAR(80),
    AlturaNormalDoRio VARCHAR(10)
);

CREATE TABLE Pet (
    Nome VARCHAR(20),
    Raca VARCHAR(20),
    ID_Pet INT PRIMARY KEY,
    Foto VARCHAR(20),
    Tipo VARCHAR(20),
    Peso VARCHAR(20),
    Status VARCHAR(20),
    Sexo VARCHAR(5)
);

CREATE TABLE AreaDeRisco (
    ID_Risco INT PRIMARY KEY,
    Descricao VARCHAR(500),
    Nome VARCHAR(20)
);

CREATE TABLE Enchente (
    Nome VARCHAR(20),
    ID_Enchente INT PRIMARY KEY,
    Data DATE);

CREATE TABLE RiscoGeralArea (
    ID_Endereco INT,
    ID_Risco INT,
    Rio_DataHora DATE
);

CREATE TABLE Post (
    Id_Post INT PRIMARY KEY,
    DataPost DATE,
    Imagem VARCHAR(100),
    ID_Usuario INT
);

CREATE TABLE Rio (
    Altura VARCHAR(10),
    Data DATE,
    Hora DATE,
    DataHora DATE PRIMARY KEY
);

CREATE TABLE Resgatar_Salva (
    NomeTutor VARCHAR(40),
    TelefoneTutor VARCHAR(13),
    LocalResgate VARCHAR(50),
    ID_Pet INT,
    ID_abrigo INT
);

CREATE TABLE HistoricoEnchente (
    ID_Enchente INT,
    ID_Endereco INT
);

CREATE TABLE e (
    ID_Usuario INT,
    ID_abrigo INT
);

CREATE TABLE Tem (
    ID_Usuario INT,
    ID_Endereco INT
);

ALTER TABLE RiscoGeralArea ADD CONSTRAINT FK_RiscoGeralArea_1
    FOREIGN KEY (ID_Endereco)
    REFERENCES Endereco (ID_Endereco);
 
 ALTER TABLE Abrigo ADD CONSTRAINT FK_Abrigo_2
    FOREIGN KEY (ID_Endereco)
    REFERENCES Endereco (id_endereco)
    ON DELETE CASCADE;
 
ALTER TABLE RiscoGeralArea ADD CONSTRAINT FK_RiscoGeralArea_2
    FOREIGN KEY (ID_Risco)
    REFERENCES AreaDeRisco (ID_Risco);
 
ALTER TABLE RiscoGeralArea ADD CONSTRAINT FK_RiscoGeralArea_3
    FOREIGN KEY (Rio_DataHora)
    REFERENCES Rio (DataHora);
 
ALTER TABLE Post ADD CONSTRAINT FK_Post_2
    FOREIGN KEY (ID_Usuario)
    REFERENCES Usuario (ID_Usuario)
    ON DELETE RESTRICT;
 
ALTER TABLE Resgatar_Salva ADD CONSTRAINT FK_Resgatar_Salva_1
    FOREIGN KEY (ID_Pet)
    REFERENCES Pet (ID_Pet);
 
ALTER TABLE Resgatar_Salva ADD CONSTRAINT FK_Resgatar_Salva_2
    FOREIGN KEY (ID_abrigo)
    REFERENCES Abrigo (ID_abrigo);
 
ALTER TABLE HistoricoEnchente ADD CONSTRAINT FK_HistoricoEnchente_1
    FOREIGN KEY (ID_Enchente)
    REFERENCES Enchente (ID_Enchente);
 
ALTER TABLE HistoricoEnchente ADD CONSTRAINT FK_HistoricoEnchente_2
    FOREIGN KEY (ID_Endereco)
    REFERENCES Endereco (ID_endereco);
 
ALTER TABLE e ADD CONSTRAINT FK_e_1
    FOREIGN KEY (ID_Usuario)
    REFERENCES Usuario (ID_Usuario)
    ON DELETE SET NULL;
 
ALTER TABLE e ADD CONSTRAINT FK_e_2
    FOREIGN KEY (ID_abrigo)
    REFERENCES Abrigo (ID_abrigo)
    ON DELETE SET NULL;
 
ALTER TABLE Tem ADD CONSTRAINT FK_Tem_1
    FOREIGN KEY (ID_Usuario)
    REFERENCES Usuario (ID_Usuario)
    ON DELETE SET NULL;
 
ALTER TABLE Tem ADD CONSTRAINT FK_Tem_2
    FOREIGN KEY (ID_Endereco)
    REFERENCES Endereco (ID_Endereco)
    ON DELETE SET NULL;