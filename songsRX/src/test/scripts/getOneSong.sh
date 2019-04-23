#! /bin/sh
echo "--- GET A SONG WITH TOKEN FROM USER 'MMUSTER' ------------------"
echo "--- First step: get tokem from user 'MMUSTER' ------------------"
token=$(curl -s --request GET http://localhost:8080/songsRX/rest/auth?userId=mmuster)
echo " "
	echo "Your token is: "$token""
echo " "
echo "--- Second step: get one song with user token ------------------"
echo "curl --request GET http://localhost:8080/songsRX/rest/songs Accept:application/json Authorization:$token"
curl --request GET http://localhost:8080/songsRX/rest/songs/7 -H Accept:application/xml -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"
