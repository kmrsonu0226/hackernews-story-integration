DROP TABLE IF EXISTS story;
  
CREATE TABLE story (
  id INT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  url VARCHAR(250) NOT NULL,
  score INT,
  time date,
  by VARCHAR(250) NOT NULL
);