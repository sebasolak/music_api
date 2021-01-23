# music_api
Api that allows you to search artist, tracks, make an account to save your favorite artist and tracks and send an email with it. Powers by https://www.theaudiodb.com/

## Run

* Download or clone repository and run it in IntelliJ IDEA
* Go to  ```meal/src/main/resources/application.properties```
and in ```spring.datasource.url``` connect with your Postgres database,
in ```spring.datasource.username and spring.datasource.password```
enter your username and password to database. Next in ```spring.mail.username and spring.mail.password``` enter valid
gmail email and password if you want api be able to send emails

# Register

* To make an account use client like Postman, go to:
```
http://localhost:{your_default_port}/registration
```
   and send a body in post request like example below:
```
{
    "firstName": "Johnny",
    "lastName": "Depp",
    "login": "johnny",
    "email": "johnny.depp@xyz.com",
    "password": "password123"
}

```
then to your email address will be send a confirmation link.
Click it and your acount will be active. 
