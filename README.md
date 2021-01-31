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

## Api map:

* You can search artist by name typing it in artistName like below (GET request):
```
http://localhost:{your_default_port}/name/{artistName}
```

* List artist discography by artist name (GET request):
```
http://localhost:{your_default_port}/albums/{artistName}
```

* Save artist to favourite by name (POST request):
```
http://localhost:{your_default_port}/name/{artistName}
```

* Delete artist from favourite by idArtist (DELETE request):
```
http://localhost:{your_default_port}/delete/{artistId}
```

* List favorite artists discography (GET request);
```
http://localhost:{your_default_port}/albums
```

* Get track by artist name and track name (GET request):
```
http://localhost:{your_default_port}/track/{artistName}/{trackName}
```

* Save track to favorite by artist name and track name (POST request):
```
http://localhost:{your_default_port}/track/{artistName}/{trackName}
```

* List favorite tracks (GET request):
```
http://localhost:{your_default_port}/tracks
```

* Delete track from favorite by idTrack (DELETE request):
```
http://localhost:{your_default_port}/delete/track/{idTrack}
```

* Send an email with your favorite tracks (GET request):
```
http://localhost:{your_default_port}/send
```

* List your favorite artists (GET request):
```
http://localhost:{your_default_port}/artists
```

