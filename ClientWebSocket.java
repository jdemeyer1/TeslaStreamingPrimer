package teslaStreamingPrimer;

import java.io.*;
import java.util.List;
import java.util.Map;

import com.neovisionaries.ws.client.*;

public class ClientWebSocket {

	private static final String SERVER = "wss://streaming.vn.teslamotors.com/streaming/";
    private static final int TIMEOUT = 5000;
    
    //The token is used for authentication.  
    //  Use this (https://tesla-api.timdorr.com/api-basics/authentication) or 
    //  this (https://www.teslaapi.io/authentication/oauth) to receive a token.
    static String bearerToken = "your token here";   
    //The vehicle ID identifies a unique vehicle.
    //  Follow this (https://tesla-api.timdorr.com/api-basics/vehicles) or
    //  this (https://www.teslaapi.io/vehicles/list) to retrieve the vehicle ID.
    //  NOTE: Streaming uses the vehicle_id value, NOT the id_s value.
    static String vehicleId = "your vehicle_id";
    
    static String connectionTemplate = ""
    		+ "{\"msg_type\":\"data:subscribe_oauth\","
    		+ "\"token\":\"%s\","
    		+ "\"value\":\"speed,odometer,soc,elevation,est_heading,est_lat,est_lng,power,shift_state,range,est_range,heading,est_corrected_lat,est_corrected_lng,native_latitude,native_longitude,native_heading,native_type,native_location_supported\","
    		+ "\"tag\":\"%s\"}";
    
	public static void main(String[] args) {
		WebSocket ws = null;
		BufferedReader in = null;
		
		System.out.println(String.format(connectionTemplate, bearerToken, vehicleId));
		
		try {
			ws = connect();
			System.out.println("EchoClient: Connected, " + ", " + ws.getState().toString());
		} catch (OpeningHandshakeException e) {
			System.out.println("Connect error: " + e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connect error");
		}
		
			try {
				in = getInput();
			} catch (IOException e) {
				e.printStackTrace();
			}

        // A text read from the standard input.
        String text = "init";

        // Read lines until "exit" is entered.
        while (text != null)
        {
        	try {
				text = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
            // If the input string is "exit".
            if (text.equals("exit"))
            {
                // Finish this application.
                break;
            }

            // Send the text to the server.
            ws.sendText(text);
        }

        // Close the web socket.
        ws.disconnect();
	}

	private static WebSocket connect() throws Exception
    {		
        return new WebSocketFactory()
            .setConnectionTimeout(TIMEOUT)
            .createSocket(SERVER)
            .addListener(new WebSocketAdapter() {
                // A text message arrived from the server.
                public void onTextMessage(WebSocket websocket, String message) {
                    System.out.println("Tesla Server Text Message: " + message);
                }
                public void onTextFrame(WebSocket websocket, WebSocketFrame websocketframe) {
                	System.out.println("Tesla Server Text Frame: " + websocketframe.getPayloadText());
                }
                public void onTextMessage(WebSocket websocket, byte[] message) {
                	System.out.println("Tesla Server Byte[] Message: " + message);
                }
                public void onBinaryMessage(WebSocket websocket, byte[] message) {
                	String stringMessage = new String(message);
                	System.out.println("Tesla Binary Message: " + stringMessage);
                }
                public void onConnectError(WebSocket websocket, WebSocketException e) {
                	System.out.println("Tesla Server Connect Error: " + e.getMessage());
                }
                public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
                	for (Map.Entry<String, List<String>> entry : headers.entrySet())
                    {
                        // Header name.
                        String name = entry.getKey();

                        // Values of the header.
                        List<String> values = entry.getValue();

                        if (values == null || values.size() == 0)
                        {
                            // Print the name only.
                            System.out.println(name);
                            continue;
                        }

                        for (String value : values)
                        {
                            // Print the name and the value.
                            System.out.format("%s: %s\n", name, value);
                        }
                    }
                }
                public void onDisconnected(WebSocket websocket,
                        WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame,
                        boolean closedByServer) {
                	System.out.println("Tesla Server Disconnected: " + clientCloseFrame.getCloseCode() + ", " + clientCloseFrame.getCloseReason());
                }
            })
            .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
            .connect();
    }
	
	private static BufferedReader getInput() throws IOException
    {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}

