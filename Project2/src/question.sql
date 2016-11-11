DROP TABLE IF EXISTS questions;
CREATE TABLE questions (
    id int NOT NULL AUTO_INCREMENT,
    question varchar (30) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO questions VALUES
(1, '1+2'),
(2, '2-3'),
(3, '3*4'),
(4, '4/0');