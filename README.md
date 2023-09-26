


<h1 align="center">Continuous Integration server for Maven-projects. </h1>


## About

A continous integration (CI) server for Maven-projects. It responds to ```PUSH```-events on GitHub and can build and test your commits, as well as mark them as successfull or failed builds. 

The server includes a page for viewing the build history. 

## Getting started

###### Dependencies

To be able to run this project you need the following program installed on your system:
- JDK 1.7
- Maven

###### Notification

The CI-server marks a commit as either ```success``` or ```failure``` . For this to work it is important that the target GitHub repository (the repo that is built and tested) and the local clone of this repo is configured properly:

1. Make sure that the web hook is configured to send ```push-events```
2. You need to generate your own personal access token. You can do it here: https://github.com/settings/tokens
3. You need to set the variable ```PERSONAL_ACCESS_TOKEN``` in the run.sh script to the hash you generated in step 2.

###### Running the program

To start the server you execute the following command: ```./run.sh```. To run the CI-server with GitHub it is recommended that you use the program ```ngrok``` to create a forwarding URL. Put this URL in the ```Settings->Webhooks->Add webhook->Payload URL```. Make sure to select ```application/JSON``` as the content-type and ```Just the push-events```

###### Testing

To test the CI-server you execute the following command: ```mvn test```

###### Show build history

Go to: ```localhost:8080/history``` on the computer running CI-Server.

## Development

This server was developed as part of the course Software Engineering Fundamentals at KTH.


