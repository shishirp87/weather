﻿# Weather
This project is an Android application that fetches weather data from the WeatherAPI. 
The app provides real-time weather updates, such as temperature, humidity, and weather conditions for selected locations.

Features:
Fetches real-time weather data using the WeatherAPI.
Displays current weather conditions, temperature, humidity, wind speed, etc.
Supports multiple locations.
Clean architecture with MVVM (Model-View-ViewModel) pattern.
Code quality monitored using SonarQube.

Prerequisites
1. An API key from WeatherAPI
2. SonarQube (optional) for code quality analysis

Getting Started
1. Clone the Repository
2. Obtain an API Key - Sign up for the WeatherAPI at https://www.weatherapi.com and obtain an API key. Add the API key to gradle.properties file
WEATHERAPI_KEY=your_api_key_here
3. Get SonarQube token and set it to "systemProp.sonar.token" in gradle.properties
4. Set SonarQube project key and organization name in gradle.properties for below values respectively "SONAR_CUBE_PROJECT_KEY" and "SONAR_CUBE_ORG"
