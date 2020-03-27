# AirlinesDB
version 
-Tomcat 9.0
-IntelliJ IDEA 2019.3.2 x64
-MySQL 8.0
-java version 13.0.2
-servlet-api 2.5
-jstl 1.2
-commons-dbcp 1.4
-javaee-api 8.0.1

Пошаговый деплой приложения на сервер tomcat:
- Зайти в папку с приложением
- Войти в командную строку и прописать "mvn package"
- Появится папка target, зайдите в нее
- В ней будет файл webapp-airlines.war, скопируйте ее
- Зайдите в папку с сервером tomcat, например "C:\Program Files\Apache Software Foundation\Tomcat 9.0", найдите папку webapp и 
  вставть туда war файл
- Далее зайдите в паке сервера tomcat в папку conf, найдите файл tomcat-user, и проверьте настроенно ли у вас подклбчкние к manager tomcat,
  если нет, то вставте эти строки в данный файл. Он вам дат прова доступа к manager tomcat, по username и password , который вы ввели.

  <role rolename = "admin-gui"/>
    <role rolename = "admin-script"/>
    <role rolename = "manager-gui"/>
    <role rolename = "manager-script"/>
    <role rolename = "manager-jne"/>
  <user username = "superadmin" password = "pass" roles="admin-gui, admin-script, manager-gui, manager-jne, manager-script" />
- Запускаем сервер, зайдите в папку bin и запустите bat файл startup
- Перейдите в браузер и введите локальный хост для входа в tomcat, например "localhost:8080" 
- найдите там вкладку Manager App нажмите на нее и по логину и паролю войдите в manager app
- Далее В прилоениях находим название нашего war файла и нажимаем на него
- Поздравляю вы запустили web-приложение
P.S Чтобы взаимодействовать с базой данных нужно всключить сервер MySQL, чтобы настроить работу под свою БД, нужно зайти в в war файл, 
  по адрессу "webapp-airlines.war\WEB-INF\classes"  и изменить под себя файл database.properties 
