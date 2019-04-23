#! /bin/sh

echo "--- POSTING A JSON SONG BY A JSON FILE ------------------"
curl --header "Content-Type: application/json" \
  --request POST \
  --data @oneSong.json \
  http://localhost:8080/songsServlet/
echo " "
echo "-------------------------------------------------------------------------------------------------"