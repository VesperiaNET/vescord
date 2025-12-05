package net.vesperia.vescord.util;

import net.vesperia.vescord.Vescord;
import net.vesperia.vescord.http.HttpRequest;
import net.vesperia.vescord.http.HttpResponse;
import net.vesperia.vescord.http.Method;
import org.json.JSONObject;

import java.io.IOException;

public class VesperUtil {

    public static JSONObject sendPrompt(String chatId, String userName, String message) throws IOException {
        HttpRequest httpRequest = new HttpRequest(Method.POST, Vescord.getConfig().getJson().getString("vesper_url"));
        httpRequest.setHeader("Authorization", "Bearer " + Vescord.getConfig().getJson().getString("vesper_token"));
        httpRequest.setHeader("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("chatId", chatId);
        body.put("userName", userName);
        body.put("message", message);
        httpRequest.setBody(body.toString());
        HttpResponse response = httpRequest.send();
        System.out.println(response.statusCode());
        System.out.println(response.headers());
        System.out.println(response.body());
        return new JSONObject(response.body());
    }

    public static JSONObject sendDevPrompt(String chatId, String userName, String devPrompt) throws IOException {
        HttpRequest httpRequest = new HttpRequest(Method.POST, Vescord.getConfig().getJson().getString("vesper_url"));
        httpRequest.setHeader("Authorization", "Bearer " + Vescord.getConfig().getJson().getString("vesper_token"));
        httpRequest.setHeader("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("chatId", chatId);
        body.put("userName", userName);
        body.put("devPrompt", devPrompt);
        httpRequest.setBody(body.toString());
        HttpResponse response = httpRequest.send();
        System.out.println(response.statusCode());
        System.out.println(response.headers());
        System.out.println(response.body());
        return new JSONObject(response.body());
    }
}
