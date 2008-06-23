CREATE TABLE sioti_profissao (
  id int(10) unsigned NOT NULL auto_increment,
  nome varchar(50) default '0',
  descricao text,
  nivel_maximo int default '100',
  id_midia_icone int default '0',
  PRIMARY KEY  (id)
);

CREATE TABLE sioti_profissao_treinamento (
  id int(10) unsigned NOT NULL auto_increment,
  id_profissao int default '0',
  id_item int default '0',

  nivel_requerido int default '0',
  nivel_profissao_requerido int default '0',
  PRIMARY KEY  (id)
);


CREATE TABLE sioti_materia_prima (
  id int(10) unsigned NOT NULL auto_increment,
  id_profissao_profissao_treinamento int default '0',
  id_item int default '0',
  quantidade int default '0',
  PRIMARY KEY  (id)
);


CREATE TABLE sioti_item (
  id int(10) unsigned NOT NULL auto_increment,
  id_tipo_item int default '0',
  nome varchar(50) default '0',
  nivel_requerido int default '0',
  atributos varchar(50) default '0',
  nivel int default '0',
  id_midia_icone int default '0',
  id_midia_no_jogo int default '0',

  PRIMARY KEY  (id)
);

CREATE TABLE sioti_tipo_item (
  id int(10) unsigned NOT NULL auto_increment,
  nome varchar(50) default '0',
  PRIMARY KEY  (id)
);

insert into sioti_tipo_item(nome) values ('capacete');
insert into sioti_tipo_item(nome) values ('colar');
insert into sioti_tipo_item(nome) values ('peitoral');
insert into sioti_tipo_item(nome) values ('cinto');
insert into sioti_tipo_item(nome) values ('calça');
insert into sioti_tipo_item(nome) values ('camisa');
insert into sioti_tipo_item(nome) values ('capa');
insert into sioti_tipo_item(nome) values ('manto');
insert into sioti_tipo_item(nome) values ('anel');
insert into sioti_tipo_item(nome) values ('luva');
insert into sioti_tipo_item(nome) values ('bracelete');
insert into sioti_tipo_item(nome) values ('sapato');
insert into sioti_tipo_item(nome) values ('amuleto');
insert into sioti_tipo_item(nome) values ('mineral');
insert into sioti_tipo_item(nome) values ('fruta');
insert into sioti_tipo_item(nome) values ('inseto');
insert into sioti_tipo_item(nome) values ('resto animal');
insert into sioti_tipo_item(nome) values ('resto humano');
insert into sioti_tipo_item(nome) values ('livro');
insert into sioti_tipo_item(nome) values ('pedra preciosa');
insert into sioti_tipo_item(nome) values ('pescado');
insert into sioti_tipo_item(nome) values ('comida');
insert into sioti_tipo_item(nome) values ('bebida');
insert into sioti_tipo_item(nome) values ('espada');
insert into sioti_tipo_item(nome) values ('machado');
insert into sioti_tipo_item(nome) values ('escudo');
insert into sioti_tipo_item(nome) values ('lança');
insert into sioti_tipo_item(nome) values ('arco');
insert into sioti_tipo_item(nome) values ('flecha');


CREATE TABLE sioti_item_magia (
  id int(10) unsigned NOT NULL auto_increment,
  id_item int default '0',
  id_magia int default '0',
  PRIMARY KEY  (id)
);


CREATE TABLE sioti_magia (
  id int(10) unsigned NOT NULL auto_increment,
  id_tipo_magia int default '0',
  nome varchar(50) default '0',
  dano int default '0',
  nivel_requerido int default '0',
  percentual_chance float default '1',

  PRIMARY KEY  (id)
);


CREATE TABLE sioti_tipo_magia (
  id int(10) unsigned NOT NULL auto_increment,
  nome varchar(50) default '0',
  PRIMARY KEY  (id)
);

insert into sioti_tipo_magia(nome) values ('natureza');
insert into sioti_tipo_magia(nome) values ('fogo');
insert into sioti_tipo_magia(nome) values ('ar');
insert into sioti_tipo_magia(nome) values ('gelo');
insert into sioti_tipo_magia(nome) values ('água');
insert into sioti_tipo_magia(nome) values ('terra');
insert into sioti_tipo_magia(nome) values ('trevas');
insert into sioti_tipo_magia(nome) values ('luz');


CREATE TABLE sioti_midia (
  id int(10) unsigned NOT NULL auto_increment,
  id_tipo_midia int,
  nome varchar(50) default '',
  PRIMARY KEY  (id)
);


CREATE TABLE sioti_midia_data (
  id int(10) unsigned NOT NULL auto_increment,
  id_midia int,
  position int,
  base64_data TEXT,
  PRIMARY KEY  (id)
);


CREATE TABLE sioti_tipo_midia (
  id int(10) unsigned NOT NULL auto_increment,
  nome varchar(50) default '0',
  id_midia_icone int default '0',
  PRIMARY KEY  (id)
);

insert into sioti_tipo_midia(nome) values ('icone');
insert into sioti_tipo_midia(nome) values ('personagem');
insert into sioti_tipo_midia(nome) values ('efeito sonoro');
insert into sioti_tipo_midia(nome) values ('trilha sonora');
insert into sioti_tipo_midia(nome) values ('item no jogo');

