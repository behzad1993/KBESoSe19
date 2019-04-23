# SongsRX

A RESTful Web Service with Jersey (JAX-RS) mit „songs“- and „auth“-Endpoints

## Simple "testing" in console with HTTPie:

### Installation:

Ubuntu: ```apt install httpie```

MacOS: ```brew install httpie```

### Example usage:

Get auth token first (necessary):

```http GET http://localhost:8080/songsRX/rest/auth?userId=mmuster```

Get all songs:

```http GET http://localhost:8080/songsRX/rest/songs Accept:application/json Authorization:token```

Get song 7:

```http GET http://localhost:8080/songsRX/rest/songs/7 Accept:application/xml Authorization:token```

Post song (i.e. create song):

```echo '{"album": "NEW album", "artist": "Me myself and I", "released": 2018, "title": "Jau"}' | http POST http://localhost:8080/songsRX/rest/songs Accept:*/* Authorization:token```

Put song (i.e. change song with id 8):

```echo '{"album": "Thank You", "artist": "David Hasselhof", "id": 8, "released": 2016, "title": "No"}' | http PUT http://localhost:8080/songsRX/rest/songs/8 Authorization:token```

Delete song 9:

```http DELETE http://localhost:8080/songsRX/rest/songs/9 Authorization:token```

