@echo off
SET /A port=%RANDOM% + 1024

cd target

start java -cp project04-1.0-SNAPSHOT.jar com.jet.edu.ClientApp %port%
start java -cp project04-1.0-SNAPSHOT.jar com.jet.edu.ClientReaderApp %port%