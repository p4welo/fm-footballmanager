DROP DATABASE IF EXISTS fm;

CREATE DATABASE fm
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_polish_ci;
USE fm;

CREATE TABLE league (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
	sid VARCHAR(32) NOT NULL,
	name VARCHAR(15) NOT NULL,
	level INT(11) NOT NULL,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE team (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
	sid VARCHAR(32) NOT NULL,
	name VARCHAR(30) NOT NULL,
	account INT(11) NOT NULL,
	league_id INT(11) DEFAULT NULL,
	type VARCHAR(30) NOT NULL,
	FOREIGN KEY (league_id) REFERENCES league(id) ON DELETE SET NULL,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE position (
    id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    sid VARCHAR(32) NOT NULL,
    short_name varchar(5) NOT NULL,
    full_name varchar(30) NOT NULL,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE player (
	id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
	sid VARCHAR(32) NOT NULL,
	name varchar(15) NOT NULL,
    surname varchar(40) NOT NULL,
	age int(11) NOT NULL DEFAULT 0,
	position_id int(11) DEFAULT NULL,
	team_sid VARCHAR(32),
	potential int(11) NOT NULL DEFAULT 0,
	passing int(11) NOT NULL DEFAULT 0,
	speed int(11) NOT NULL DEFAULT 0,
	stamina int(11) NOT NULL DEFAULT 0,
	energy int(11) NOT NULL DEFAULT 0,
	crossing int(11) NOT NULL DEFAULT 0,
	heading int(11) NOT NULL DEFAULT 0,
    marking int(11) NOT NULL DEFAULT 0,
    shots int(11) NOT NULL DEFAULT 0,
    tackling int(11) NOT NULL DEFAULT 0,
    dribbling int(11) NOT NULL DEFAULT 0,
    goalkeeping int(11) NOT NULL DEFAULT 0,
    FOREIGN KEY (position_id) REFERENCES position (id) ON DELETE SET NULL,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE user (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
	sid VARCHAR(32) NOT NULL,
	login VARCHAR(15) NOT NULL,
	password VARCHAR(65) NOT NULL,
	email VARCHAR(30) NOT NULL,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE manager (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
	sid VARCHAR(32) NOT NULL,
	name VARCHAR(15) NOT NULL,
	surname VARCHAR(40) NOT NULL,
	team_id INT(11) DEFAULT NULL,
	user_id INT(11) NOT NULL,
	FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE SET NULL,
	FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE authority (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
	sid VARCHAR(32) NOT NULL,
	user_id INT(11) NOT NULL,
	authority VARCHAR(30) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE season (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
	sid VARCHAR(32) NOT NULL,
	number INT(5),
	league_id INT(11) NOT NULL,
	FOREIGN KEY (league_id) REFERENCES league(id) ON DELETE CASCADE,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE team_record (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
	sid VARCHAR(32) NOT NULL,
	team_sid VARCHAR(32) NOT NULL,
	season_id INT(11) NOT NULL,
	team_name VARCHAR(30) NOT NULL,
	place INT NOT NULL DEFAULT 0,
	round_number INT NOT NULL DEFAULT 0,
    points_count INT NOT NULL DEFAULT 0,
    goals_scored INT NOT NULL DEFAULT 0,
    goals_allowed INT NOT NULL DEFAULT 0,
    goals_difference INT NOT NULL DEFAULT 0,
    wins_count INT NOT NULL DEFAULT 0,
    draws_count INT NOT NULL DEFAULT 0,
    loses_count INT NOT NULL DEFAULT 0,
	FOREIGN KEY (season_id) REFERENCES season(id) ON DELETE CASCADE,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE match_game (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
	sid VARCHAR(32) NOT NULL,
	match_date timestamp NOT NULL,
	season_id INT(11) NOT NULL,
	round INT NOT NULL DEFAULT 0,
	host_scores INT DEFAULT 0,
	host_sid VARCHAR(32) NOT NULL,
	host_name VARCHAR(32) NOT NULL,
	guest_scores INT DEFAULT 0,
	guest_sid VARCHAR(32) NOT NULL,
	guest_name VARCHAR(32) NOT NULL,
	FOREIGN KEY (season_id) REFERENCES season(id) ON DELETE CASCADE,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE match_story (
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
	sid VARCHAR(32) NOT NULL,
	match_game_id INT(11) NOT NULL,
	story_line VARCHAR(40),
	time timestamp,
	FOREIGN KEY (match_game_id) REFERENCES match_game(id) ON DELETE CASCADE,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE name (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    sid VARCHAR(32) NOT NULL,
    value VARCHAR(30) DEFAULT NULL,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE surname (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    sid VARCHAR(32) NOT NULL,
    value VARCHAR(30) DEFAULT NULL,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE position_area (
    id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    sid VARCHAR(32) NOT NULL,
    area INT(5),
    position_id int(11) NOT NULL,
    FOREIGN KEY (position_id) REFERENCES position (id) ON DELETE CASCADE,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE system_parameter (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	object_state VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    sid VARCHAR(32) NOT NULL,
    pkey VARCHAR(30) NOT NULL,
    value VARCHAR(30) DEFAULT NULL,
	UNIQUE (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;