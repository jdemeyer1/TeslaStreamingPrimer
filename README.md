# Tesla Streaming Primer
This repository contains a small Java program that demonstrates the basics of streaming data from a Tesla electric vehicle.

## Getting Started
The Java program requires an authenitcation token and vehicle identifier.  Use the APIs described [here](https://tesla-api.timdorr.com/) or [here](https://www.teslaapi.io/) to get the token and identifier.  I recommend [Postman](https://www.postman.com/) to work with the APIs.  See the source code for more details.

The Java program imports the nv-websocket-client library located [here](https://github.com/TakahikoKawasaki/nv-websocket-client).

## Running the Program
I execute the program in [Eclispe](https://www.eclipse.org/downloads/packages/release/kepler/sr1/eclipse-ide-java-developers).

When the program starts, it displays the following text to the Console.

{"msg_type":"data:subscribe_oauth","token":"your token","value":"speed,odometer,soc,elevation,est_heading,est_lat,est_lng,power,shift_state,range,est_range,heading,est_corrected_lat,est_corrected_lng,native_latitude,native_longitude,native_heading,native_type,native_location_supported","tag":"your vehicle_id"}
EchoClient: Connected, , OPEN
Cache-Control: no-cache, no-store, private, s-max-age=0
Connection: upgrade
Date: Sat, 02 Jan 2021 14:58:27 GMT
Sec-WebSocket-Accept: 123123123123
Strict-Transport-Security: max-age=15724800; includeSubDomains
Strict-Transport-Security: max-age=31536000; includeSubDomains
Upgrade: websocket
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
Tesla Binary Message: {"msg_type":"control:hello","connection_timeout":0}


