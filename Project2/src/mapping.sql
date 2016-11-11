DROP TABLE IF EXISTS mappings;
CREATE TABLE mappings (
    id int NOT NULL AUTO_INCREMENT,
    Kid int NOT NULL,
    Qid int NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO mappings VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4);