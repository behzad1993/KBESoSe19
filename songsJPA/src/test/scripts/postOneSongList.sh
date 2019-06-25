#! /bin/sh

echo "--- First step: get token from user 'MMUSTER' ------------------"
token=$(curl -s --request GET http://localhost:8080/songsWS/rest/auth?userId=mmuster)
echo " "
echo "Your token is: "$token""
echo " "

echo "--- POSTING A JSON SONG LIST ------------------"
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"isPublic": true, "song": [{"id": 2}, {"id": 4}, {"id": 6}]}' \
  http://localhost:8080/songsWS/rest/songLists -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"