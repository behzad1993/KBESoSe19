#! /bin/sh

echo "--- First step: get token from user 'MMUSTER' ------------------"
token=$(curl -s --request GET http://localhost:8080/songsRX/rest/auth?userId=mmuster)
echo " "
echo "Your token is: "$token""
echo " "

echo "--- POSTING A JSON SONG LIST BY A XML FILE ------------------"
curl --header "Content-Type: application/xml" \
  --request POST \
  --data @aSongList.xml \
  http://localhost:8080/songsRX/rest/songLists -H Authorization:$token
echo " "
echo "-------------------------------------------------------------------------------------------------"
