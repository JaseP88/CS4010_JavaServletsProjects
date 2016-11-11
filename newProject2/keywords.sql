DROP TABLE IF EXISTS keywords;
CREATE TABLE keywords (
    id int NOT NULL AUTO_INCREMENT,
    keyword varchar (30) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO keywords VALUES
(1, 'addition'),
(2, 'subtraction'),
(3, 'multiplication'),
(4, 'division');