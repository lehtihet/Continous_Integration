package com.group21.continous_integration;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.apache.commons.io.IOUtils;


/**
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println("* Handling request: " + target);

        GitRequest req = null;
        if(request.getMethod().equals("POST")){

            String payload = IOUtils.toString(request.getReader());

            req = new GitRequest(payload);

            if(target.equalsIgnoreCase("/")){
                Integration integ = new Integration(req);

                BuildResult compilation = integ.build();
                BuildHistory.getInstance().insert(compilation);

                boolean testStatus = integ.test();

                System.out.println("* Compilation returned: " + compilation.status);
                System.out.println("* Test returned: " + testStatus);

                response.getWriter().println("CI job done");
                response.getWriter().println("Compilation returned: " + compilation.status);
                response.getWriter().println("Test returned: " + testStatus);

                //Send status
                Notification notification = new Notification();
                
                try{
                    int statusCode = notification.SendStatus(
                        req.statuses_url.substring(0, req.statuses_url.length() - 5), 
                        req.commit_hash, 
                        compilation.status ? "success" : "failure", 
                        App.PERSONAL_ACCESS_TOKEN
                    );

                    System.out.println("* Successfully sent status (status code: " + statusCode + ")");
                }
                catch(Exception e){
                    System.out.println("* Error when sending status: " + e.getMessage());
                }
            }
        }

        if(request.getMethod().equals("GET")){
            if(target.equalsIgnoreCase("/history")){
                String specifiedBuild = request.getParameter("build");
                if(specifiedBuild != null){
                    System.out.println("* Sending info for build #" + specifiedBuild);
                    response.getWriter().println(BuildHistory.getInstance().getBuildInfoWebPage(Integer.parseInt(specifiedBuild)));
                }
                else{
                    System.out.println("* Sending build history list");
                    response.getWriter().println(BuildHistory.getInstance().getBuildListWebPage());
                }
            }
        }
    }
}
