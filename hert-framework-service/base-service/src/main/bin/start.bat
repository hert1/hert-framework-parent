@echo off & setlocal enabledelayedexpansion

set LIB_JARS
cd ..\lib
for %%i in (*) do set LIB_JARS=!LIB_JARS!;..\lib\%%i
cd ..\bin

set LIB_JARS
java -Xms512m -Xmx512m -classpath ..\conf;%LIB_JARS% com.hert.base.BaseApplication
goto end

:end
pause
