DROP TABLE IF EXISTS songlist_song;
DROP TABLE IF EXISTS song;
DROP TABLE IF EXISTS songlist;
DROP TABLE IF EXISTS user_tab;

create table user_tab
        (id integer,
        userId VARCHAR(20) not null primary key,
        firstName VARCHAR(100) not null,
        lastName VARCHAR(100) not null);

create table song
        (id integer primary key,
        title VARCHAR(200) not null,
        artist VARCHAR(200),
        album VARCHAR(100),        
        released integer);


START TRANSACTION;

DELETE FROM song;
DELETE FROM user_tab;

INSERT INTO user_tab(id, userId, firstName, lastName)
VALUES (1, 'mmuster', 'Maxime', 'Muster');
INSERT INTO user_tab(id, userId, firstName, lastName)
VALUES (2, 'eschuler', 'Elena', 'Schuler');
INSERT INTO user_tab(id, userId, firstName, lastName)
VALUES (3, 'bkar', 'Behzad', 'Karimi');
INSERT INTO user_tab(id, userId, firstName, lastName)
VALUES (4, 'uov', 'Uli', 'Overdieck');


INSERT INTO song(id, title, artist, album, released)
VALUES (1, 'Can’t Stop the Feeling', 'Justin Timberlake', 'Trolls', 2016);
INSERT INTO song(id, title, artist, album, released)
VALUES (2, 'Mom', 'Meghan Trainor, Kelli Trainor', 'Thank You', 2016);
INSERT INTO song(id, title, artist, released)
VALUES (3, 'Team', 'Iggy Azalea', 2016);
INSERT INTO song(id, title, artist, album, released)
VALUES (4, 'Ghostbusters (I’m not afraid)', 'Fall Out Boy, Missy Elliott', 'Ghostbusters', 2016);
INSERT INTO song(id, title, artist, album, released)
VALUES (5, 'Bad Things', 'Camila Cabello, Machine Gun Kelly', 'Bloom', 2017);
INSERT INTO song(id, title, artist, album, released)
VALUES (6, 'I Took a Pill in Ibiza', 'Mike Posner', 'At Night, Alone.', 2016);
INSERT INTO song(id, title, artist, album, released)
VALUES (7, 'i hate u, i love u', 'Gnash', 'Top Hits 2017', 2017);
INSERT INTO song(id, title, artist, album, released)
VALUES (8, 'No', 'Meghan Trainor', 'Thank You', 2016);
INSERT INTO song(id, title, artist, album, released)
VALUES (9, 'Private Show', 'Britney Spears', 'Glory', 2016);
INSERT INTO song(id, title, artist, album, released)
VALUES (10, '7 Years', 'Lukas Graham', 'Lukas Graham (Blue Album)', 2015);


commit;
