#!/bin/bash

if [ ! -d "target" ] ; then
	  echo "Installing Maven dependencies..."
	    mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
fi

PERSONAL_ACCESS_TOKEN="<YOUR TOKEN>"

mvn compile -q &&
mvn exec:java -Dexec.mainClass="com.group21.continous_integration.App" -Dexec.args="$PERSONAL_ACCESS_TOKEN"
