@echo off
title AI �ڿ��� ó�� �߸� ���� �ڵ� ����
:home
cls
echo.
echo �Ȧ�����������������������������������������������������������������������������������������������������������������������������
echo �� *AI �ڿ��� ó�� �߸� ����
echo ��               
echo �� ���� �ڵ� �����Դϴ�.
echo �� mariaDB, java ��ġ�� �ʼ��Դϴ�.
echo ��
echo �Ʀ�����������������������������������������������������������������������������������������������������������������������������
echo.
set /p a=  Gemini API KEY �Է� �� Enter : 

cls
java -DGEMINI_API_KEY=%a% -jar build\libs\aiMysteryGame-0.0.1-SNAPSHOT.jar
pause
goto exit