/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgjnd7divelearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author chloejones
 */
public class NYTNewsManager {
    
    private String urlString = "";

    private final String baseUrlString = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
    private final String apiKey = "7BLPOjE9MFSgUW6LKQQhfSYUwoAqWAh0";
    private String searchString = "African-Americans";
    
    private URL url;
    private ArrayList<NYTNewsStory> newsStories;
    
    
    public NYTNewsManager() {
        newsStories = new ArrayList<>();
    }
    
    public void load(String searchString) throws Exception {     
        if (searchString == null || searchString.equals("")) {
            throw new Exception("The search string was empty.");
        }
        
        this.searchString = searchString;
        System.out.println(searchString + "load nYTmanager");
        String encodedSearchString; 
        
        try {
            encodedSearchString = URLEncoder.encode(searchString, "UTF-8"); 
        } catch(UnsupportedEncodingException ex){
            throw ex; 
        }
        
        urlString = baseUrlString + "?q=" + encodedSearchString + "&api-key=" + apiKey;
        
        System.out.println("urlString: " + urlString);
        System.out.println(searchString + "load nYTmanager2");
        try {
            url = new URL(urlString); 
        } catch(MalformedURLException muex){
            throw muex; 
        }
        System.out.println(url + "3");
        String jsonString = ""; 
        
        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream())); 
            
            String inputLine; 
            
            while((inputLine = in.readLine()) != null){
                jsonString += inputLine; 
            }
            
            in.close();
            
        } catch(IOException ioex){
            throw ioex; 
        }
        System.out.println(searchString + "load nYTmanager4");
        System.out.print("jsonString: " + jsonString);
        
        try {
            parseJsonNewsFeed(jsonString); 
        } catch (Exception ex){
            throw ex; 
        }
    }
    
    private void parseJsonNewsFeed(String jsonString) throws Exception {
        
        newsStories.clear();
        System.out.println("news stories cleared, parsing");
        if (jsonString == null || jsonString == "") return;
        
        JSONObject jsonObj;
        try {
            jsonObj = (JSONObject)JSONValue.parse(jsonString);
        } catch (Exception ex) {
            throw ex;
        }
        
        if (jsonObj == null) return;
        
        String status = "";
        try {
            status = (String)jsonObj.get("status");
        } catch (Exception ex) {
            throw ex;
        }
        
        if (status == null || !status.equals("OK")) {
            throw new Exception("Status returned from API was not OK.");
        }
        
        JSONObject response;
        try {
            response = (JSONObject)jsonObj.get("response");
        } catch (Exception ex) {
            throw ex;
        }
        
        JSONArray docs;
        try {
            docs = (JSONArray)response.get("docs");
        } catch (Exception ex) {
            throw ex;
        }
      
        for (Object doc : docs) {
            try {
                JSONObject story = (JSONObject)doc;
                String webUrl = (String)story.getOrDefault("web_url", "");
                String snippet = (String)story.getOrDefault("snippet", "");
                String leadParagraph = (String)story.getOrDefault("lead_paragraph", "");
                String source = (String)story.getOrDefault("source", "");
                String newsDesk = (String)story.getOrDefault("news_desk", "");
                String sectionName = (String)story.getOrDefault("section_name", "");
                JSONObject headlineObj = (JSONObject)story.getOrDefault("headline", null);
                String headline = "";
                if (headlineObj != null) {
                    headline = (String)headlineObj.getOrDefault("main", "");
                }
                
                System.out.println("headline: " + headline + "\n");
                System.out.println("webUrl: " + webUrl + "\n");
                System.out.println("snippet: " + snippet + "\n");
                System.out.println("leadParagraph: " + leadParagraph + "\n");
                System.out.println("newsDesk: " + newsDesk + "\n");
                System.out.println("sectionName: " + sectionName + "\n");
                System.out.println("source: " + source + "\n");
                System.out.println("------------------------------------------------------\n");
                
                NYTNewsStory newsStory = new NYTNewsStory(webUrl, headline, snippet, leadParagraph, newsDesk, sectionName, source );
                newsStories.add(newsStory);
                
            } catch (Exception ex) {
                throw ex;
            }
            
        }
        
    }
    
    public ArrayList<NYTNewsStory> getNewsStories() {
        return newsStories;
    }
    
    public int getNumNewsStories() {        
        return newsStories.size();
    }   
}
