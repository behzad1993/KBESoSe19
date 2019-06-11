# SongsWS

(Hibernate) - songsWS mit Datenbankanbindung und mit neuem Endpoint songLists (Belegaufgabe 4)

## Fill DB (only in case of emergency i.e. renewal of DB)

```cd sql/```

Login to DB:

```psql -h db.f4.htw-berlin.de _s0565477__songsws _s0565477__songsws_generic```

sql/songsDB.sql holds data for initialization (and renewal) of DB:

```\i songsDB.sql```

After that start und get token (see below). Than:

```\i songsDB_songList_with_owner.sql```


## NOT NECESSARY ANY MORE: Extreme ugly workaround for 10-connection-limit problem

factory.close() (Entity manager factory) is not executed on tomcat shutdown but only 10 connections to htw postgres are allowed.

Login to DB prior to all development:

```psql -h db.f4.htw-berlin.de _s0556056__songsdb02 _s0556056__songsdb02_generic```

Hold this connection the whole time while testing and playing around!

When limit is reached find out the id's of non psql-connections:

    SELECT *
    FROM   pg_stat_activity
    WHERE  usename = '_s0556056__songsdb02_generic';


    SELECT pid
    FROM   pg_stat_activity
    WHERE  usename = '_s0556056__songsdb02_generic';
   
After that delete these old connections (example deletion of pid 402):

    SELECT pg_terminate_backend(402)
    FROM   pg_stat_activity
    WHERE  usename = '_s0556056__songsdb02_generic' 
    AND    402 <> pg_backend_pid();


## Simple "testing" in console with HTTPie:

### Installation:

Ubuntu: ```apt install httpie```

MacOS: ```brew install httpie```

### Example usage:

Get auth token first (necessary):

```http GET http://localhost:8080/songsWS/rest/auth?userId=mmuster```

Sign In

```http GET http://localhost:8080/songsWS/rest/auth?userId=mmuster&secret=321dwssap```

#### Songs:

Get all songs:

```http GET http://localhost:8080/songsWS/rest/songs Accept:application/json Authorization:token```

Get song 7:

```http GET http://localhost:8080/songsWS/rest/songs/7 Accept:application/xml Authorization:token```

Post song (i.e. create song):

```echo '{"album": "NEW album", "artist": "Me myself and I", "released": 2018, "title": "Jau"}' | http POST http://localhost:8080/songsWS/rest/songs Authorization:token```

as XML:

```echo '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><song><title>Tester</title><artist>Tester</artist><album>Tester</album><released>9999</released></song>' | http POST http://localhost:8080/songsWS/rest/songs Authorization:28a76383-22f9-4916-852e-be05046c9899```

Put song (i.e. change song with id 7):

```echo '{"album": "Thank You", "artist": "David Hasselhof", "id": 7, "released": 2016, "title": "No"}' | http PUT http://localhost:8080/songsWS/rest/songs/7 Authorization:token```

#### SongLists

A) Get song lists for mmuster:

```http GET http://localhost:8080/songsWS/rest/songLists?userId=mmuster Accept:application/json Authorization:token```

B) Get song list 3:

```http GET http://localhost:8080/songsWS/rest/songLists/3 Accept:application/xml Authorization:token```

C) Post song list (i.e. create song list):

```echo '{"isPublic": true, "song": [{"id": 2}, {"id": 4}, {"id": 6}]}' | http POST http://localhost:8080/songsWS/rest/songLists Accept:text/plain Authorization:token```

D) Delete song list 3 (belongs to mmuster, so she is the only one allowed to do that):

```http DELETE http://localhost:8080/songsWS/rest/songLists/3 Authorization:token```
