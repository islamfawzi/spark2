package com.islam.spark2;

import static spark.Spark.get;
import static spark.Spark.post;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import lombok.Data;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.sql2o.Sql2o;

public class BlogService2 {
    
	private static final int HTTP_BAD_REQUEST = 400;
    
    interface Validable {
        boolean isValid();
    }
    
        
    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }
    
    
	public static void main(String[] args) {
			
		Sql2o sql2o = new Sql2o("jdbc:mysql://localhost/blog", "root", "1234");
		
		Sql2oModel model = new Sql2oModel(sql2o);
		post("/posts", (request, response) -> {
	        ObjectMapper mapper = new ObjectMapper();
	        NewPostPayload creation = mapper.readValue(request.body(), NewPostPayload.class);
	        if (!creation.isValid()) {
	            response.status(HTTP_BAD_REQUEST);
	            return "is nor valid";
	        }
	        String inserted = model.createPost(creation.getTitle(), creation.getContent(), creation.getCategories());
	        response.status(200);
	        response.type("application/json");
	        return dataToJson(inserted);
	    });
		
		// get all post (using HTTP get method)
	    get("/posts", (request, response) -> {
	        response.status(200);
	        
	        try{
	          response.type("application/json");
	          return dataToJson(model.getAllPosts());
	        }catch(Exception e){
	        	return e.getMessage();
	        }
	    });

	    post("/posts/:uuid/comments", (request, response) -> {
	        ObjectMapper mapper = new ObjectMapper();
	        NewCommentPayload creation = mapper.readValue(request.body(), NewCommentPayload.class);
	        if (!creation.isValid()) {
	            response.status(HTTP_BAD_REQUEST);
	            return "";
	        }
	        int post = Integer.parseInt(request.params(":uuid"));
	        if (!model.existPost(post)){
	            response.status(400);
	            return "";
	        }
	        String comment_inserted = model.createComment(post, creation.getAuthor(), creation.getContent());
	        response.status(200);
	        response.type("application/json");
	        return comment_inserted;
	    });

	    get("/posts/:uuid/comments", (request, response) -> {
	        int post = Integer.parseInt(request.params(":uuid"));
	        if (!model.existPost(post)) {
	            response.status(400);
	            return "";
	        }
	        response.status(200);
	        response.type("application/json");
	        return dataToJson(model.getAllCommentsOn(post));
	    });
	    
	    get("/", (request, response) -> {
	    	return "Hello World!";
	    });
	}
	
	


}
