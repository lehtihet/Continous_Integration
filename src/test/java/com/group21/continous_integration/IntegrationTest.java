package com.group21.continous_integration;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class IntegrationTest
{

    // Self testing
    // ============
    @Test
    public void dogfood()
    {
        GitRequest req = new GitRequest(
            "Oliver", 
            "CIServer", 
            "https://github.com/lehtihet/Continous_Integration.git", 
            "cbd65ee1ecc826a078a1fbd507f720a7a6a858b5", 
            "main", 
            "test@example.com", 
            "N/A"
        );

        Integration integ = new Integration(req);

        // Test that compilation succeeds:
        BuildResult compilation = integ.build();
        assertTrue(compilation.status);

        // No self testing since that would infinite-loop..
    }

    // Testing of branch assesments
    // ============================
    @Test
    public void assessment()
    {
        GitRequest req = new GitRequest(
            "Oliver", 
            "CIServer", 
            "https://github.com/lehtihet/Continous_Integration.git", 
            "cbd65ee1ecc826a078a1fbd507f720a7a6a858b5", 
            "assesments", 
            "test@example.com", 
            "N/A"
        );
    
        Integration integ = new Integration(req);

        // Test that compilation succeeds:
        BuildResult compilation = integ.build();
        assertTrue(compilation.status);

        // Test that testing succeeds:
        boolean testing = integ.test();
        assertFalse(testing);
    }
}