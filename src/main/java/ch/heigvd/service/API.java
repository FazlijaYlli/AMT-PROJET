package ch.heigvd.service;

import ch.heigvd.entities.*;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class API {


    static public JsonObject createResponse(String successMessage, Jsonable data) {
        return API.success(successMessage)
                .add("data", data.toJson())
                .build();
    }

    static public JsonObject createErrorResponse(String errorMessage) {
        return API.error(errorMessage)
                .build();
    }

    static public JsonObject createServerListResponse(JsonObjectBuilder status) {
        return status
                .add("data", serverList())
                .build();
    }

    static private JsonArrayBuilder serverList() {
        JsonArrayBuilder serverArray = Json.createArrayBuilder();

        ServerService serverService = new ServerService();

        for (Server server : serverService.listServers()) {
            serverArray.add(server.toJson());
        }

        return serverArray;
    }

    static public JsonObject createCategoryAndChannelListResponse(JsonObjectBuilder status, Server server) {
        return status
                .add("data", categoryAndChannelList(server))
                .build();
    }

    static private JsonObjectBuilder categoryAndChannelList(Server server) {
        JsonObjectBuilder dataObject = Json.createObjectBuilder();

        dataObject.add("id", server.getId())
                .add("name", server.getName())
                .add("owner", server.getOwner().getId());

        JsonArrayBuilder categoriesArray = Json.createArrayBuilder();

        for (Category category : server.getCategories()) {
            JsonObjectBuilder categoryObject = Json.createObjectBuilder();
            categoryObject.add("id", category.getId())
                    .add("name", category.getName())
                    .add("channels", channelList(category));
            categoriesArray.add(categoryObject);
        }

        dataObject.add("categories", categoriesArray);

        return dataObject;
    }

    static private JsonArrayBuilder channelList(Category category) {
        JsonArrayBuilder channelArray = Json.createArrayBuilder();

        for (Channel channel : category.getChannelsInCategory()) {
            channelArray.add(channel.toJson());
        }
        return channelArray;
    }


    static public JsonObject createMessagesResponse(JsonObjectBuilder status, Channel channel) {
        return status
                .add("data", messageList(channel))
                .build();
    }

    static private JsonObjectBuilder messageList(Channel channel) {
        JsonObjectBuilder dataObject = Json.createObjectBuilder();

        dataObject.add("id", channel.getId())
                .add("name", channel.getName());

        JsonArrayBuilder messageArray = Json.createArrayBuilder();

        for (Message msg : channel.getMessages()) {
            messageArray.add(msg.toJson());
        }

        dataObject.add("messages", messageArray);

        return dataObject;
    }


    static private JsonObjectBuilder success(String successMessage) {
        return Json.createObjectBuilder()
                .add("status", "200")
                .add("message", successMessage);
    }

    static private JsonObjectBuilder error(String errorMessage) {
        return Json.createObjectBuilder()
                .add("status", "400")
                .add("message", errorMessage);
    }

}
