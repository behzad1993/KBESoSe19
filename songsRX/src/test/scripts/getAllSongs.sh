#! /bin/sh
echo "--- GET ALL SONGS WITH TOKEN FROM USER 'MMUSTER' ------------------"
echo "--- First step: get tokem from user 'MMUSTER' ------------------"
token=$(curl -s --request GET http://localhost:8080/songsRX/rest/auth?userId=mmuster)
echo " "
	echo "Your token is: "$token""
echo " "
echo "--- Second step: get all songs with user token ------------------"
echo "curl --request GET http://localhost:8080/songsRX/rest/songs Accept:application/json Authorization:$token"
curl --header "Accept:application/json" \
     --request GET http://localhost:8080/songsRX/rest/songs -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"
