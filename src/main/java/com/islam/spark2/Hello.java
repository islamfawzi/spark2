package com.islam.spark2;
import static spark.Spark.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Hello {
	
    public static void main(String[] args) {
    	
        get("/hello", (req, res) -> "Hello Spark World");
  
    }
}