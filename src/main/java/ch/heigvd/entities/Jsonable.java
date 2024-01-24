package ch.heigvd.entities;

import jakarta.json.JsonObjectBuilder;

public interface Jsonable {
    JsonObjectBuilder toJson();
}
