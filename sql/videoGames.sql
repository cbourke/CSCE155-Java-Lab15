
DROP TABLE IF EXISTS availability;
DROP TABLE IF EXISTS platform;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS publisher;

#--INDEX and KEY are the same in MySQL

CREATE TABLE publisher (
       publisher_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
       name       VARCHAR(255)  NOT NULL
)Engine=InnoDB,COLLATE=latin1_general_cs;

INSERT INTO publisher (publisher_id, name) VALUES (1, 'LucasArts');
INSERT INTO publisher (publisher_id, name) VALUES (2, 'Sony Computer Entertainment');
INSERT INTO publisher (publisher_id, name) VALUES (3, 'Square Enix');
INSERT INTO publisher (publisher_id, name) VALUES (4, 'Sega');
INSERT INTO publisher (publisher_id, name) VALUES (5, 'Nintendo');
INSERT INTO publisher (publisher_id, name) VALUES (6, 'Rockstar Games');
INSERT INTO publisher (publisher_id, name) VALUES (7, 'Blizzard Entertainment');
INSERT INTO publisher (publisher_id, name) VALUES (8, 'Capcom');
INSERT INTO publisher (publisher_id, name) VALUES (9, 'Atari');
INSERT INTO publisher (publisher_id, name) VALUES (10, 'Eidos');
INSERT INTO publisher (publisher_id, name) VALUES (11, 'Valve');
INSERT INTO publisher (publisher_id, name) VALUES (12, 'Namco');
INSERT INTO publisher (publisher_id, name) VALUES (13, 'Delphine Software');

SELECT * FROM publisher;

CREATE TABLE platform (
       platform_id INT NOT NULL AUTO_INCREMENT,
       name VARCHAR(255) NOT NULL,
       PRIMARY KEY(platform_id)
)Engine=InnoDB,COLLATE=latin1_general_cs;

INSERT INTO platform (platform_id, name) VALUES (1, 'Xbox');
INSERT INTO platform (platform_id, name) VALUES (2, 'Xbox 360');
INSERT INTO platform (platform_id, name) VALUES (3, 'PlayStation');
INSERT INTO platform (platform_id, name) VALUES (4, 'PlayStation 2');
INSERT INTO platform (platform_id, name) VALUES (5, 'PlayStation 3');
INSERT INTO platform (platform_id, name) VALUES (6, 'NES');
INSERT INTO platform (platform_id, name) VALUES (7, 'PC');
INSERT INTO platform (platform_id, name) VALUES (8, 'Mac');
INSERT INTO platform (platform_id, name) VALUES (9, 'Game Boy');
INSERT INTO platform (platform_id, name) VALUES (10, 'Super NES');

SELECT * FROM platform;

CREATE TABLE game (
       game_id INT NOT NULL AUTO_INCREMENT,
       name      VARCHAR(255) NOT NULL,
       publisher_id INT NOT NULL,
       FOREIGN KEY(publisher_id) REFERENCES publisher(publisher_id),
       PRIMARY KEY(game_id)
)Engine=InnoDB,COLLATE=latin1_general_cs;

INSERT INTO game (game_id, name, publisher_id) VALUES (1, 'GTA IV', 6);
INSERT INTO game (game_id, name, publisher_id) VALUES (2, 'GTA 3', 6);
INSERT INTO game (game_id, name, publisher_id) VALUES (3, 'GTA 2', 6);
INSERT INTO game (game_id, name, publisher_id) VALUES (4, 'GTA', 6);
INSERT INTO game (game_id, name, publisher_id) VALUES (5, 'Legend of Zelda', 5);
INSERT INTO game (game_id, name, publisher_id) VALUES (6, 'Super Mario Brothers', 5);
INSERT INTO game (game_id, name, publisher_id) VALUES (7, 'Zelda II: The Adventure of Link', 5);
INSERT INTO game (game_id, name, publisher_id) VALUES (8, 'Mega Man 3', 8);
#--Publisher should be Capcom, intentionally made incorrect to correct later
INSERT INTO game (game_id, name, publisher_id) VALUES (9, 'Mega Man 4', 10);
INSERT INTO game (game_id, name, publisher_id) VALUES (10, 'Tetris', 5);
INSERT INTO game (game_id, name, publisher_id) VALUES (11, 'Super Mario Land', 5);
INSERT INTO game (game_id, name, publisher_id) VALUES (12, 'Tomb Raider', 10);
INSERT INTO game (game_id, name, publisher_id) VALUES (13, 'Portal', 11);
INSERT INTO game (game_id, name, publisher_id) VALUES (14, 'Portal 2', 11);
INSERT INTO game (game_id, name, publisher_id) VALUES (15, 'Katamari Damacy', 12);
INSERT INTO game (game_id, name, publisher_id) VALUES (16, 'Lego Star Wars', 1);
INSERT INTO game (game_id, name, publisher_id) VALUES (17, 'Tie Fighter', 1);
INSERT INTO game (game_id, name, publisher_id) VALUES (18, 'X-Wing vs Tie Fighter', 1);
INSERT INTO game (game_id, name, publisher_id) VALUES (19, 'Contra', 8);
INSERT INTO game (game_id, name, publisher_id) VALUES (20, 'Out of this World', 13);

CREATE TABLE availability (
       availability_id INT NOT NULL AUTO_INCREMENT,
       game_id INT NOT NULL,
       platform_id INT NOT NULL,
       publish_year INT,
       PRIMARY KEY(availability_id),
       KEY(availability_id,game_id,platform_id),
       FOREIGN KEY(game_id) REFERENCES game(game_id),
       FOREIGN KEY(platform_id) REFERENCES platform(platform_id)
)Engine=InnoDB,COLLATE=latin1_general_cs;

INSERT INTO availability (game_id, platform_id, publish_year) VALUES (1,2,2008);

#--GTA 4
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (1, 7, 2008);

#--NES games
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (5, 6, 1987);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (6, 6, 1985);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (7, 6, 1988);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (8, 6, 1990);

INSERT INTO availability (game_id, platform_id, publish_year) VALUES (10, 9, 1989);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (11, 9, 1989);

#--Lego Star Wars
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (16, 1, 2005);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (16, 4, 2005);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (16, 7, 2005);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (16, 8, 2005);

#--Out of this world
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (20, 7, 1992);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (20, 10, 1992);

#--Tomb Raider
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (12, 3, 1996);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (12, 7, 1996);

#--Portal
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (13, 5, 2007);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (13, 7, 2007);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (13, 2, 2007);

#--Portal 2
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (14, 2, 2011);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (14, 5, 2011);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (14, 7, 2011);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (14, 8, 2011);

#--Katamari Damacy
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (15, 4, 2004);

#--Lucas Arts
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (17, 7, 1994);
INSERT INTO availability (game_id, platform_id, publish_year) VALUES (18, 7, 1997);

