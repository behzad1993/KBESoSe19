START TRANSACTION;

DELETE FROM songlist_song;
DELETE FROM songlist;

INSERT INTO songlist(id, ispublic, owner)
VALUES (1, false, 'bkar');
INSERT INTO songlist(id, ispublic, owner)
VALUES (3, true, 'mmuster');
INSERT INTO songlist(id, ispublic, owner)
VALUES (4, false, 'mmuster');
INSERT INTO songlist(id, ispublic, owner)
VALUES (5, false, 'eschuler');

INSERT INTO songlist_song(songlist_id, songs_id)
VALUES (1, 1);
INSERT INTO songlist_song(songlist_id, songs_id)
VALUES (1, 2);
INSERT INTO songlist_song(songlist_id, songs_id)
VALUES (1, 4);
INSERT INTO songlist_song(songlist_id, songs_id)
VALUES (1, 6);
INSERT INTO songlist_song(songlist_id, songs_id)
VALUES (1, 10);
INSERT INTO songlist_song(songlist_id, songs_id)
VALUES (3, 2);
INSERT INTO songlist_song(songlist_id, songs_id)
VALUES (3, 4);
INSERT INTO songlist_song(songlist_id, songs_id)
VALUES (3, 6);
INSERT INTO songlist_song(songlist_id, songs_id)
VALUES (4, 6);
INSERT INTO songlist_song(songlist_id, songs_id)
VALUES (5, 8);

commit;
