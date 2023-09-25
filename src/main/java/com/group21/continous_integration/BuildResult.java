package com.group21.continous_integration;

import java.io.Serializable;


public class BuildResult implements Serializable
{
    public String repository = null;
    public String branch = null;
    public String linkedCommit = null;
    public boolean status = false;
    public String message = null;
    public String log = "";
}