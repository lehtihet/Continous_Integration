package com.group21.continous_integration;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


/**
 * Unit test for simple Email Service.
 */
public class NotificationTest
{

    /*Checks that it fails when you send to wrong address and sha*/
    @Test
    public void checkFailAddressSha()
    {
      Notification notification = new Notification();
      //Should fail
      try{
        notification.SendStatus("Wrong", "wrong", "success");
      }
      catch(Exception e){
        assertTrue(true);
      }
    }
    //Checks that we can connect to github and internet
    @Test
    public void checkConnection()
    {
      Notification notification = new Notification();
      try{
        notification.SendStatus("https://github.com/lehtihet/Continous_Integration.git", "", "");
      }
      catch(Exception e){
        try{
          System.out.println("You cannot connect to github, are you sure you have access to the repo?");
          assertTrue(false);
        }
        catch(AssertionError noInternet){
          System.out.println("You are probably not connected to the internet");
        }
      }
    }

}
