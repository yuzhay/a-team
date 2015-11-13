@echo off
cd target

start java -cp project04-1.0-SNAPSHOT.jar com.jet.edu.ClientApp
start java -cp project04-1.0-SNAPSHOT.jar com.jet.edu.ClientReaderApp

pause