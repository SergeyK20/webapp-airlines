create database airlines;
use airlines;

CREATE TABLE city (
  Id int NOT NULL AUTO_INCREMENT,
  name_city varchar(40) NOT NULL,
  PRIMARY KEY (Id),
  UNIQUE KEY name_city_unic (name_city)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE typea (
  Id int NOT NULL AUTO_INCREMENT,
  name_type varchar(11) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY typea_unic (name_type)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE route (
  Id int NOT NULL AUTO_INCREMENT,
  id_from int NOT NULL,
  id_to int NOT NULL,
  time_travel smallint NOT NULL,
  PRIMARY KEY (Id),
  UNIQUE KEY route_unic (id_from, id_to, time_travel),
  CONSTRAINT route_ibfk_1 FOREIGN KEY (id_from) REFERENCES city (Id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT route_ibfk_2 FOREIGN KEY (id_to) REFERENCES city (Id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE plane (
  Id int NOT NULL AUTO_INCREMENT,
  name_plane varchar(20) NOT NULL,
  id_type int NOT NULL,
  PRIMARY KEY (Id),
  UNIQUE KEY name_plane_unic (name_plane),
  CONSTRAINT plane_ibfk_1 FOREIGN KEY (id_type) REFERENCES typea (Id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE flights (
  id int NOT NULL AUTO_INCREMENT,
  id_flights int NOT NULL,
  id_route int NOT NULL,
  date date NOT NULL,
  time time NOT NULL,
  id_plane int NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY flights_unic (id_flights),
  CONSTRAINT flights_ibfk_2 FOREIGN KEY (id_route) REFERENCES route (Id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT flights_ibfk_3 FOREIGN KEY (id_plane) REFERENCES plane (Id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into city (name_city) values ('Tokio');
insert into city (name_city) values ('Saint Petersburg');
insert into city (name_city) values ('Moscow');
insert into city (name_city) values ('Samara');
insert into city (name_city) values ('Kazan');

insert into route (id_from, id_to,time_travel) values ( 1, 2, 123);
insert into route (id_from, id_to,time_travel) values ( 2, 1, 123);
insert into route (id_from, id_to,time_travel) values ( 3, 2, 200);
insert into route (id_from, id_to,time_travel) values ( 1, 4, 150);
insert into typea (name_type) values ('Passenger');
insert into typea (name_type) values ('Cargo');

insert into plane (Name_plane, id_type) values ('Airbus A320',1);
insert into plane (Name_plane, id_type) values ('Airbus A330',1);
insert into plane (Name_plane, id_type) values ('An 12', 2);
insert into plane (Name_plane, id_type) values ('Tu 330', 2);

insert into flights (id_flights, id_route, date, time, id_plane)
values (123, 1, '2019-05-02', '12:50', 1);
insert into flights (id_flights, id_route, date, time, id_plane)
values (12, 2, '2012-07-29', '13:50', 4);
insert into flights (id_flights, id_route, date, time, id_plane)
values (31, 3, '2020-05-02', '22:00', 2);
insert into flights (id_flights, id_route, date, time, id_plane)
values (44, 4, '2018-05-02', '22:50', 3);


