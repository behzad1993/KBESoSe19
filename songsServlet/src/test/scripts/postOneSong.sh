#! /bin/sh

echo "--- POSTING A JSON SONG ------------------"
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"title" : "a new title", "artist" : "YOU", "album" : "First Album", "released" : 2017 }' \
  http://localhost:8080/songsServlet/
echo " "
echo "-------------------------------------------------------------------------------------------------"