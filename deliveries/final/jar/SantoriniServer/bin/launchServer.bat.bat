@echo off
set JLINK_VM_OPTIONS=
set DIR=%~dp0
"%DIR%\java" %JLINK_VM_OPTIONS% -m it.polimi.ingsw.PSP25/it.polimi.ingsw.PSP25.Server.Server %*
