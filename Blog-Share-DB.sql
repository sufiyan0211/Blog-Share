SHOW DATABASES;

USE BlogDb;
SHOW TABLES;

DROP TABLE user;
TRUNCATE Table user; 


CREATE TABLE User (
id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name varchar(255),
username varchar(255) NOT NULL,
email varchar(255),
password varchar(255) NOT NULL,
bio varchar(255)
);


DESCRIBE User;
DESCRIBE BlogDb.`blog`;


SELECT * FROM BlogDb.`user`;
SELECT * FROM BlogDb.`blog`;