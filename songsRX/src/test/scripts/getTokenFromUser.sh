#! /bin/sh
echo "--- GET TOKEN FROM USER 'MMUSTER' ------------------"
token=$(curl -s --request GET http://localhost:8080/songsRX/rest/auth?userId=mmuster)
echo " "
	echo "Your token is: $token"
echo " "
echo "-------------------------------------------------------------------------------------------------"