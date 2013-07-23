#!/bin/sh
mvn clean package
tar czfv converter.tar.gz konvertiere* target/converter-1.0-jar-with-dependencies.jar
