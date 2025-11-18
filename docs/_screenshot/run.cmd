@echo off
setlocal enabledelayedexpansion

set counter=1

:loop
rem Format counter to two digits
if !counter! lss 10 (
    set filename=0!counter!.png
) else (
    set filename=!counter!.png
)

echo Taking screenshot: !filename!
nircmd.exe savescreenshot !filename!

rem Add to Git, commit, push
git add "!filename!"
git commit -m "Add screenshot !filename!" >nul 2>&1
git push

rem Wait 30 seconds
timeout /t 30 >nul

rem Increment counter
set /a counter+=1
goto loop
