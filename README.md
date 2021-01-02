# Tesla Streaming Primer
This repository contains a small Java program that demonstrates the basics of streaming data from a Tesla electric vehicle.

## Getting Started
The Java program requires an authenitcation token and vehicle identifier.  Use the APIs described [here](https://tesla-api.timdorr.com/) or [here](https://www.teslaapi.io/) to get the token and identifier.  Note that the vehicle must be [on-line](https://tesla-api.timdorr.com/vehicle/commands/wake) to stream data.  I recommend [Postman](https://www.postman.com/) to work with the APIs.  See the source code for more details.

The Java program imports the nv-websocket-client library located [here](https://github.com/TakahikoKawasaki/nv-websocket-client).  It builds on their sample program.

## Running the Program
I execute the program in [Eclispe](https://www.eclipse.org/downloads/packages/release/kepler/sr1/eclipse-ide-java-developers).

When the program starts successfully, it displays the following text to the Console (Sec-WebSocket-Accept has been masked).  Be sure the vehicle is on-line.

```
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
```
The last line, Tesla Binary Message, is from the Tesla server.  The Tesla server waits for the subscibe response from the program.  Copy the first line (starts with {"msg_type") to the clipboard.  Paste it after the last line and press Enter.  If the vehicle is on-line, the response appears (<lat> and <long> appear here instead of the actual latitude and longitude values).
```
Tesla Binary Message: {"msg_type":"data:update","tag":"your vehicle_id","value":"1609599517956,,7158.7,81,317,139,<lat>,<long>,0,,195,147,138,<lat>,<long>,<lat>,<long>,139,wgs,1"}
```
Data is streamed when there is a change in a parameter.  If there are no changes after ten seconds, the Tesla server sends this indicating the vehicle is no longer connected.
```
Tesla Binary Message: {"msg_type":"data:error","tag":"your vehicle_id","value":"disconnected","error_type":"vehicle_disconnected"}
```
To start the stream again, send the subscribe response as described above.

