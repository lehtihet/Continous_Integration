package com.group21.continous_integration;

import org.json.JSONObject;
import org.json.JSONException;

public class GitRequest{
    public String author;
    public String repository;
    public String cloneUrl;
    public String commit_hash;
    public String branch;
    public String email_addr;
    public String statuses_url;

    public GitRequest(String payload){
        //make JSON object
        try {
            JSONObject jsonObject = new JSONObject(payload);
        
            //Set all parameters
            this.author = jsonObject.getJSONObject("pusher").get("name").toString();
            this.repository = jsonObject.getJSONObject("repository").get("name").toString();
            this.cloneUrl = jsonObject.getJSONObject("repository").get("clone_url").toString();
            this.branch = jsonObject.get("ref").toString().replaceFirst("refs/heads/", "");
            this.commit_hash = jsonObject.getJSONObject("head_commit").get("id").toString();
            this.email_addr = jsonObject.getJSONObject("pusher").get("email").toString();
            this.statuses_url = jsonObject.getJSONObject("repository").get("statuses_url").toString();
            
        } catch (JSONException e) {
            return;
        }
       
    }

    public GitRequest(String author, String repository, String cloneURL, String commit_hash, String branch, String email_addr, String statuses_url) {
        // Set parameters without payload object
        this.author = author;
        this.repository = repository;
        this.cloneUrl = cloneURL;
        this.commit_hash = commit_hash;
        this.branch = branch;
        this.email_addr = email_addr;
        this.statuses_url = statuses_url;

    }

    public String getJSONRepresentation()
    {
        JSONObject json = new JSONObject();
        JSONObject repository = new JSONObject();
        JSONObject head_commit = new JSONObject();
        JSONObject pusher = new JSONObject();

        json.put("ref", "refs/heads/" + branch);

        //repository
        repository.put("name", this.repository);
        repository.put("statuses_url", statuses_url);
        repository.put("clone_url", cloneUrl);

        //commitHash
        head_commit.put("id", commit_hash);

        //pusher
        pusher.put("email", email_addr);
        pusher.put("name", author);

        //add repository obj to json
        json.put("repository", repository);
        json.put("head_commit", head_commit);
        json.put("pusher", pusher);

        return json.toString();
    }

}