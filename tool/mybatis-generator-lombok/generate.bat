@echo on
if not exist src\main\java       md src\main\java
if not exist src\main\resources  md src\main\resources
del src\main\java\*              /q /f /s
del src\main\resources\*         /q /f /s
java -jar mybatis-generator-core-1.3.7.jar -configfile generatorConfig.xml -overwrite