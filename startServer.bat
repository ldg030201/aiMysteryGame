@echo off
title AI 자연어 처리 추리 게임 자동 실행
:home
cls
echo.
echo ┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo │ * 셜록 프로젝트 최최최종 Made By 율곡 Lee2
echo │               
echo ┃ 서버 자동 실행입니다.
echo ┃ mariaDB, java 설치는 필수입니다.
echo ┃
echo ┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
set /p a=  Gemini API KEY 입력 후 Enter : 

cls
echo.
echo ┍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo │ * 실행하실 AI를 선택해주세요
echo │ 
echo ┃ 1. gemini-2.5-flash (일부 무료 / 속도 빠름 / 안정성 떨어짐)
echo ┃
echo ┃ 2. gemini-2.5-pro (유료 / 속도 느림 / 안정성 높음)
echo ┃
echo ┕━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
set /p b= 실행하실 모델 번호 선택 후 Enter : 

if "%b%" == "1" (
    set c=gemini-2.5-flash
) else if "%b%" == "2" (
    set c=gemini-2.5-pro
)

cls
java -DGEMINI_API_KEY=%a% -DMODEL=%c% -jar build\libs\aiMysteryGame-0.0.1.jar
pause
goto exit