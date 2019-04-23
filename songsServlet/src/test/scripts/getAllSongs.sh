#! /bin/sh

echo "--- GET ALL SONGS ------------------"
curl --request GET \
  http://localhost:8080/songsServlet/?all
echo " "
echo "-------------------------------------------------------------------------------------------------"