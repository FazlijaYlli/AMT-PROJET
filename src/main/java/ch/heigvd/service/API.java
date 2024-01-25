package ch.heigvd.service;

import ch.heigvd.entities.*;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.List;

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

    static public JsonObject createServerListResponse(String successMessage, List<Server> servers) {
        return API.success(successMessage)
                .add("data", serverList(servers))
                .build();
    }

    static public JsonObject createCategoryAndChannelListResponse(String successMessage, Server server) {
        return API.success(successMessage)
                .add("data", categoryAndChannelList(server))
                .build();
    }

    static public JsonObject createMessagesResponse(String successMessage, Channel channel) {
        return API.success(successMessage)
                .add("data", messageList(channel))
                .build();
    }

    static private JsonArrayBuilder serverList(List<Server> servers) {
        JsonArrayBuilder serverArray = Json.createArrayBuilder();

        for (Server server : servers) {
            serverArray.add(server.toJson());
        }

        return serverArray;
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
