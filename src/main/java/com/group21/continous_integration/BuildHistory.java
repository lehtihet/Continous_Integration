package com.group21.continous_integration;

import java.util.ArrayList;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.File;


public class BuildHistory implements Serializable
{
    private static BuildHistory instance;
    private static File fsResource = new File("build_history");

    private ArrayList<BuildResult> builds;

    public BuildHistory()
    {
        builds = new ArrayList<BuildResult>();
    }

    public static BuildHistory getInstance()
    {
        return instance;
    }

    public void insert(BuildResult buildResult)
    {
        builds.add(buildResult);
        save();
    }

    public static void save()
    {
        try {
            FileOutputStream fileOut = new FileOutputStream(fsResource);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(instance);
            out.close();
            fileOut.close();
            System.out.println("* Saved history to: " + fsResource.toPath().toString());
        } 
        catch (IOException i) {
           i.printStackTrace();
        }
    }

    public static void load()
    {
        try {
            FileInputStream fileIn = new FileInputStream(fsResource);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            instance = (BuildHistory) in.readObject();
            in.close();
            fileIn.close();
        } 
        catch (Exception e) {            
            instance = new BuildHistory();
        }
    }    

    public String getBuildListWebPage()
    {
        StringBuilder buff = new StringBuilder();

        buff.append("<html>");
        buff.append("<br>");
        buff.append("<h1 align='center'>BUILD HISTORY</h1>");

        buff.append("<table style='width:100%'>");

        buff.append("<tr>");
        buff.append("<th>" + "<u>BUILD</u>" + "</th>");
        buff.append("<th>" + "<u>REPO</u>" + "</th>");
        buff.append("<th>" + "<u>BRANCH</u>" + "</th>");
        buff.append("<th>" + "<u>COMMIT</u>" + "</th>");
        buff.append("<th>" + "<u>STATUS</u>" + "</th>");
        buff.append("<th>" + "<u>MESSAGE</u>" + "</th>");
        buff.append("</tr>");

        buff.append("<tr>");
        buff.append("<th>" + "&nbsp;" + "</th>");
        buff.append("</tr>");

        int id = 1;
        for(BuildResult build : builds)
        {
            buff.append("<tr>");
            buff.append("<td align='center'>" + "<a href='/history?build=" + id + "'>#" + id + "</a>" + "</th>");
            buff.append("<td align='center'>" + (build.repository != null ? build.repository : "N/A") + "</th>");
            buff.append("<td align='center'>" + (build.branch != null ? build.branch : "N/A") + "</th>");
            buff.append("<td align='center'>" + (build.linkedCommit != null ? build.linkedCommit : "N/A") + "</th>");
            buff.append("<td align='center'>" + (build.status ? "PASSED" : "FAILED") + "</th>");
            buff.append("<td align='center'>" + (build.message != null ? build.message : "N/A") + "</th>");
            buff.append("</tr>");
            id++;
        }

        buff.append("</table>");
        buff.append("</html>");

        return buff.toString();
    }

    public String getBuildInfoWebPage(int id)
    {
        StringBuilder buff = new StringBuilder();

        id--;
        
        if(id < 0 || id > builds.size()){
            buff.append("<html>");
            buff.append("<a href='/history'>BACK</a><br><br>");
            buff.append("Build not found!");
            buff.append("</html>");
            
            return buff.toString();
        }

        BuildResult build = builds.get(id);
        id++;

        buff.append("<html>");
        buff.append("<a href='/history'>BACK</a>");
        buff.append("<h1 align='center'>BUILD #" + id + "</h1>");

        buff.append("<table style='width:100%'>");

        buff.append("<tr>");
        buff.append("<th>" + "<u>REPO</u>" + "</th>");
        buff.append("<th>" + "<u>BRANCH</u>" + "</th>");
        buff.append("<th>" + "<u>COMMIT</u>" + "</th>");
        buff.append("<th>" + "<u>STATUS</u>" + "</th>");
        buff.append("<th>" + "<u>MESSAGE</u>" + "</th>");
        buff.append("</tr>");

        buff.append("<tr>");
        buff.append("<th>" + "&nbsp;" + "</th>");
        buff.append("</tr>");

        buff.append("<tr>");
        buff.append("<td align='center'>" + (build.repository != null ? build.repository : "N/A") + "</th>");
        buff.append("<td align='center'>" + (build.branch != null ? build.branch : "N/A") + "</th>");
        buff.append("<td align='center'>" + (build.linkedCommit != null ? build.linkedCommit : "N/A") + "</th>");
        buff.append("<td align='center'>" + (build.status ? "PASSED" : "FAILED") + "</th>");
        buff.append("<td align='center'>" + (build.message != null ? build.message : "N/A") + "</th>");
        buff.append("</tr>");

        buff.append("</table><br>");

        buff.append("<h3>BUILD & TESTING LOG</h3>");
        buff.append("<p>" + build.log.replaceAll("\n", "<br>")+ "</p>");

        buff.append("</html>");

        return buff.toString();
    }
}