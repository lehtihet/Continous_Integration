package com.group21.continous_integration;

import org.junit.Test;
import org.json.JSONObject;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class GitRequestTest
{

    /*Test if the request class creates a json object and stores the variables correct */
    @Test
    public void requestTest()
    {
        String originalJson = new GitRequest("johan", "continous_integration", "www.clone.com", "commit_hash", "master", "test@test.com", "www.statuses.com").getJSONRepresentation();

        GitRequest reconstruction = new GitRequest(originalJson);

        // The commit id is stored correctly
        assertEquals(reconstruction.commit_hash, "commit_hash");

        // The author is stored correctly
        assertEquals(reconstruction.author, "johan");

        // The clone url is stored correctly
        assertEquals(reconstruction.cloneUrl, "www.clone.com");
         
        // The status url is stored correctly
        assertEquals(reconstruction.statuses_url, "www.statuses.com");

        // The repository is stored correctly
        assertEquals(reconstruction.repository,"continous_integration");

        // The branch refference is stored correctly
        assertEquals(reconstruction.branch, "master");

        // The email is stored correctly
        assertEquals(reconstruction.email_addr, "test@test.com");
    }
}