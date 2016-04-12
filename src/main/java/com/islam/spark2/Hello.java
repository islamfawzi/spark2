package com.islam.spark2;
import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;


public class Hello {
	
	public static void main(String[] args) {
    	
		FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
		Configuration freeMarkerConfiguration = new Configuration();
    	freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(BlogService.class, "/"));
    	freeMarkerEngine.setConfiguration(freeMarkerConfiguration);
    	
    	
        get("/hello", (req, res) -> "Hello Spark World");
        get("/index", (req, res) -> {
        	res.status(200);
        	res.type("text/html");
            Map<String, Object> attributes = new HashMap<>();
            //attributes.put("posts", model.getAllPosts());
            return freeMarkerEngine.render(new ModelAndView(attributes, "hello.ftl"));
        });
        
        
  
    }
}