#! /bin/sh

echo "--- First step: get token from user 'ESCHULER' ------------------"
token=$(curl -s --request GET http://localhost:8080/songsRX/rest/auth?userId=eschuler)
echo " "
echo "Your token is: "$token""
echo " "

echo " "
echo "--- Try to delete public SONG LIST of other user (3 belongs to mmuster) -> SHOULD FAIL WITH 403 ------------------"

curl --request DELETE http://localhost:8080/songsWS/rest/songLists/3 -H Authorization:$token

echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- Try to delete private SONG LIST of other user (4 belongs to mmuster) -> SHOULD FAIL WITH 403 ------------------"

curl --request DELETE http://localhost:8080/songsWS/rest/songLists/4 -H Authorization:$token

echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- Try to delete not existing SONG LIST -> SHOULD FAIL WITH 404 ------------------"

curl --request DELETE http://localhost:8080/songsWS/rest/songLists/666 -H Authorization:$token

echo " "
echo "-------------------------------------------------------------------------------------------------"
echo " "
echo "Hint: Happy case deletion is tested by Postman (including prior POST of songlist in xml and json)"
