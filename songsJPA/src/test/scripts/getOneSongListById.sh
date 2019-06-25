#! /bin/sh

echo "--- GET SONG LIST BY ID TESTING ------------------"

echo "--- First step: get token from user 'MMUSTER' ------------------"
token=$(curl -s --request GET http://localhost:8080/songsWS/rest/auth?userId=mmuster)
echo " "
echo "MMUSTER's token is: "$token""

echo " "
echo "--- XML: get public song list 3 with user token of owner ------------------"
echo "curl --request GET http://localhost:8080/songsWS/rest/songLists/3 Accept:application/xml Authorization:$token"
curl --request GET http://localhost:8080/songsWS/rest/songLists/3 -H Accept:application/xml -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- JSON: get public song list 3 with user token of owner ------------------"
echo "curl --request GET http://localhost:8080/songsWS/rest/songLists/3 Accept:application/json Authorization:$token"
curl --request GET http://localhost:8080/songsWS/rest/songLists/3 -H Accept:application/json -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- XML: get private song list 4 with user token of owner ------------------"
echo "curl --request GET http://localhost:8080/songsWS/rest/songLists/4 Accept:application/xml Authorization:$token"
curl --request GET http://localhost:8080/songsWS/rest/songLists/4 -H Accept:application/xml -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- JSON: get private song list 4 with user token of owner ------------------"
echo "curl --request GET http://localhost:8080/songsWS/rest/songLists/4 Accept:application/json Authorization:$token"
curl --request GET http://localhost:8080/songsWS/rest/songLists/4 -H Accept:application/json -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- XML: get NOT EXISTING song list 666 -> SHOULD FAIL WITH 404 ------------------"
echo "curl --request GET http://localhost:8080/songsWS/rest/songLists/666 Accept:application/xml Authorization:$token"
curl --request GET http://localhost:8080/songsWS/rest/songLists/666 -H Accept:application/xml -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- JSON: get NOT EXISTING song list 666 -> SHOULD FAIL WITH 404 ------------------"
echo "curl --request GET http://localhost:8080/songsWS/rest/songLists/666 Accept:application/json Authorization:$token"
curl --request GET http://localhost:8080/songsWS/rest/songLists/666 -H Accept:application/json -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- First step: get token from user 'ESCHULER' ------------------"
token=$(curl -s --request GET http://localhost:8080/songsWS/rest/auth?userId=eschuler)
echo " "
echo "ESCHULER's token is: "$token""

echo " "
echo "--- XML: get public song list 3 with other user token ------------------"
echo "curl --request GET http://localhost:8080/songsWS/rest/songLists/3 Accept:application/xml Authorization:$token"
curl --request GET http://localhost:8080/songsWS/rest/songLists/3 -H Accept:application/xml -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- JSON: get public song list 3 with other user token ------------------"
echo "curl --request GET http://localhost:8080/songsWS/rest/songLists/3 Accept:application/json Authorization:$token"
curl --request GET http://localhost:8080/songsWS/rest/songLists/3 -H Accept:application/json -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- XML: get private song list 4 with other user token -> SHOULD FAIL WITH 403 ------------------"
echo "curl --request GET http://localhost:8080/songsWS/rest/songLists/4 Accept:application/xml Authorization:$token"
curl --request GET http://localhost:8080/songsWS/rest/songLists/4 -H Accept:application/xml -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- JSON: get private song list 4 with other user token -> SHOULD FAIL WITH 403 ------------------"
echo "curl --request GET http://localhost:8080/songsWS/rest/songLists/4 Accept:application/json Authorization:$token"
curl --request GET http://localhost:8080/songsWS/rest/songLists/4 -H Accept:application/json -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"