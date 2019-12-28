package com.willhaycode.rockoff.lipwig;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;

import com.badlogic.gdx.Gdx;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Host extends WebSocketClient {
    private String id = "";
    private JSONParser parser = new JSONParser();
    public Host(String server) throws URISyntaxException {
        super(new URI(server));
        this.connect();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Gdx.app.log("Lipwig", "Connection Opened");
        this.send("create", new JSONArray(), new JSONArray());
    }

    public void send(String event, JSONArray data, JSONArray recipient) {
        JSONObject message = new JSONObject();
        message.put("event", event);
        message.put("data", data);
        message.put("sender", this.id);
        message.put("recipient", recipient);
        this.send(message.toJSONString());
    }

    @Override
    public void onMessage(String message) {
        Gdx.app.log("Message", message);
        try {
            JSONObject messageJSON = (JSONObject) parser.parse(message);
            String event = messageJSON.get((Object) "event").toString();
            JSONArray data = (JSONArray) messageJSON.get((Object) "data");
            String sender = messageJSON.get((Object) "sender").toString();
            JSONArray recipient = (JSONArray) messageJSON.get((Object) "recipient");
            Gdx.app.log("Event", event);

            if (event.equals("created")) {
                Gdx.app.log("Created", "Now");
                
                String code = data.get(0).toString();
                this.id = code;
                Gdx.app.log("Code!", code);
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onError(Exception ex) {
        // TODO Auto-generated method stub

    }
}