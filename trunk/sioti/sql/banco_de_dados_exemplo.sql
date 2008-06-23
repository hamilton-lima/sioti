CREATE DATABASE java;

USE java;

CREATE TABLE pagamento (
  id int(10) unsigned NOT NULL auto_increment,
  descricao varchar(50) default '0',
  data date default '0000-00-00',
  valor float default '0',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

GRANT ALL PRIVILEGES ON java.* TO 'teste'@'localhost' IDENTIFIED BY '123' WITH GRANT OPTION;
FLUSH PRIVILEGES;

GRANT ALL PRIVILEGES ON java.* TO 'teste'@'%' IDENTIFIED BY '123' WITH GRANT OPTION;
FLUSH PRIVILEGES;
