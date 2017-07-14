#!/bin/bash

echo "Running a load of 1000 Create + 1000 Delete = 2000 Posts"
echo ""

for i in {1..1000}
do
   ./createFPC-CURL.sh
   ./modifyFPC-CURL.sh
   ./deleteFPC-CURL.sh
done

echo "Done."
echo ""
