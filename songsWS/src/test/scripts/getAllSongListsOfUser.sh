#! /bin/sh

echo "--- GET ALL SONG LIST FOR USING TESTING ------------------"

echo "--- Get token from user 'MMUSTER' ------------------"
token=$(curl -s --request GET http://localhost:8080/songsRX/rest/auth?userId=mmuster)
echo " "
echo "MMUSTER's token is: "$token""
echo " "

echo " "
echo "--- XML: get all songs lists of MMUSTER with user token of owner -> show 3 (public) and 4 (private) ------------------"
echo "curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=mmuster -H Accept:application/xml -H Authorization:$token"
curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=mmuster -H Accept:application/xml -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- JSON: get all songs lists of MMUSTER with user token of owner -> show 3 (public) and 4 (private) ------------------"
echo "curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=mmuster -H Accept:application/json -H Authorization:$token"
curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=mmuster -H Accept:application/json -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- XML: get all songs lists (all private) of ESCHULER with other user token -> 404 ------------------"
echo "curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=eschuler -H Accept:application/xml -H Authorization:$token"
curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=eschuler -H Accept:application/xml -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- JSON: get all songs lists (all private) of ESCHULER with other user token -> 404 ------------------"
echo "curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=eschuler -H Accept:application/json -H Authorization:$token"
curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=eschuler -H Accept:application/json -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"
echo " "

echo "--- Get token from user 'ESCHULER' ------------------"
token=$(curl -s --request GET http://localhost:8080/songsRX/rest/auth?userId=eschuler)
echo " "
echo "ESCHULER's token is: "$token""

echo " "
echo "--- XML: get all songs lists (all private) of ESCHULER with user token of owner -> show 5 (private) ------------------"
echo "curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=eschuler -H Accept:application/xml -H Authorization:$token"
curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=eschuler -H Accept:application/xml -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- JSON: get all songs lists (all private) of ESCHULER with user token of owner -> show 5 (private) ------------------"
echo "curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=eschuler -H Accept:application/json -H Authorization:$token"
curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=eschuler -H Accept:application/json -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- XML: get all songs lists of MMUSTER with other user token -> show 3 (public) and NOT 4 (private) ------------------"
echo "curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=mmuster -H Accept:application/xml -H Authorization:$token"
curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=mmuster -H Accept:application/xml -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- JSON: get all songs lists of MMUSTER with other user token -> show 3 (public) and NOT 4 (private) ------------------"
echo "curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=mmuster -H Accept:application/json -H Authorization:$token"
curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=mmuster -H Accept:application/json -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- XML: get all songs lists of NOT EXISTING USER with other user token -> 404 ------------------"
echo "curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=notexister -H Accept:application/xml -H Authorization:$token"
curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=notexister -H Accept:application/xml -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo " "
echo "--- JSON: get all songs lists of NOT EXISTING USER with other user token -> 404 ------------------"
echo "curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=notexister -H Accept:application/json -H Authorization:$token"
curl --request GET http://localhost:8080/songsRX/rest/songLists?userId=notexister -H Accept:application/json -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"