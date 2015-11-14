@echo off
SET /A port=%RANDOM% + 1024

cd target

start java -Dfile.encoding=Cp866 -cp project04-1.0-SNAPSHOT.jar com.jet.edu.client.ClientApp %port%
start java -Dfile.encoding=Cp866 -cp project04-1.0-SNAPSHOT.jar com.jet.edu.client.ClientReaderApp %port%