#!/bin/bash
set -e

until nc -4 -z -w 2 mc_server 9876; do
  sleep 1
done

java -jar PWebsitePanel.jar