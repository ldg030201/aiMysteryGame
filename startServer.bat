@echo off
title AI 자연어 처리 추리 게임 자동 실행
:home
cls
echo.
echo ┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo │ *AI 자연어 처리 추리 게임
echo │               
echo ┃ 서버 자동 실행입니다.
echo ┃ mariaDB, java 설치는 필수입니다.
echo ┃
echo ┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
set /p a=  Gemini API KEY 입력 후 Enter : 

cls
java -DGEMINI_API_KEY=%a% -jar build\libs\aiMysteryGame-0.0.1-SNAPSHOT.jar
pause
goto exit