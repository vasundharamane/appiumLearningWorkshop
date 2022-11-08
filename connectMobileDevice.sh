#!/usr/bin/env bash
# Environment Variables

sleep 20


echo "Connect Device -  click yes on Device"

adb kill-server
adb start-server
adb devices
adb connect 192.168.0.104:5555

adb connect 192.168.0.104:5555

adb devices

#appium


java -cp "appium-docker.jar:appium-docker-tests.jar:libs/*" org.testng.TestNG testng.xml
