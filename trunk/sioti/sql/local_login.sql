
CREATE TABLE mdl_user (
  id int(10) unsigned NOT NULL auto_increment,
  username varchar(50) default '0',
  password varchar(32) default '0',
  PRIMARY KEY  (id)
);

INSERT INTO mdl_user values (1,'foo', md5('bar'));

CREATE TABLE mdl_role (
  id int(10) unsigned NOT NULL auto_increment,
  shortname varchar(50) default '0',
  PRIMARY KEY  (id)
);

INSERT INTO mdl_role values (1,'admin');

CREATE TABLE mdl_role_assignments (
  roleid int(10) unsigned,
  userid int(10) unsigned
);

INSERT INTO mdl_role_assignments values (1,1);
