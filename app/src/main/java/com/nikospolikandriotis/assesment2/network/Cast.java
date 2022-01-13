package com.nikospolikandriotis.assesment2.network;

import org.json.JSONException;
import org.json.JSONObject;

public class Cast {
    public String name;
    public String character;

    public static Cast build(JSONObject object) throws JSONException {
        Cast cast = new Cast();
        cast.name = object.getString("name");
        cast.character = object.getString("character");
        return cast;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return name + " (" + character + ")";
    }
}
