CREATE DATABASE MICRO_SERVICE
GO

USE MICRO_SERVICE
GO

CREATE TABLE ADRESS(
   id_adress INT,
   country VARCHAR(255),
   zip VARCHAR(255),
   city VARCHAR(255),
   street VARCHAR(255),
   number VARCHAR(255),
   PRIMARY KEY(id_adress)
);

CREATE TABLE APIKEY(
   apiKey INT,
   quota INT,
   creation_date DATE,
   expiration_date DATE,
   PRIMARY KEY(apiKey)
);

GO

CREATE TABLE USERS(
   id_user INT,
   username VARCHAR(255),
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   role VARCHAR(50) NOT NULL,
   isLock BIT,
   last_connection DATETIME,
   pin INT,
   apiKey INT NOT NULL,
   id_adress INT NOT NULL,
   PRIMARY KEY(id_user),
   UNIQUE(apiKey),
   FOREIGN KEY(apiKey) REFERENCES APIKEY(apiKey),
   FOREIGN KEY(id_adress) REFERENCES ADRESS(id_adress)
);

GO

CREATE TABLE MAIL(
   id_mail INT,
   object VARCHAR(255),
   sending_date DATETIME,
   apiKey INT NOT NULL,
   PRIMARY KEY(id_mail),
   FOREIGN KEY(apiKey) REFERENCES APIKEY(apiKey)
);

GO