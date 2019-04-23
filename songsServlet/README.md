# SongsServlet


## Simple "testing" in console with HTTPie:

### Installation:

Ubuntu: ```apt install httpie```

MacOS: ```brew install httpie```

### Example usage:

Get all:

```http GET http://localhost:8080/songsServlet?all``` or ```http GET http://localhost:8080/songsServlet/?all```

Get song by Id:

```http GET http://localhost:8080/songsServlet?songId=6``` or ```http GET http://localhost:8080/songsServlet/?songId=6```

Post song:

```echo '{"title" : "a new title", "artist" : "YOU", "album" : "First Album", "released" : 2017 }' | http POST http://localhost:8080/songsServlet```

Check it's existence (after post):

```http GET http://localhost:8080/songsServlet/?songId=11```

Set accept headers:

```http -v GET http://localhost:8080/songsServlet?songId=6 Accept:'application/json, */*'```

