### MovieFlix 
This is a microservice project.
## Features:
1. [User Registration](https://github.com/Avijeetas/UserRegistration)
  * the backend is done using spring boot.
  * Db: Postgres
  * Redis is used for temporarily storing new users before pushing them into the database
    * filtering temporary users
2. Movie Service
![image](https://github.com/Avijeetas/movieflix/assets/18629416/6c058d7a-b649-44b4-bcab-b2b4304b3ba2)

* Backend is written in spring boot
* Database MySQL
* Java 21
* use SSO to validate every get or post operation API by calling the user registration service
3. Movie info
  * A crawler written in Python to crawl information about movies
  * Facilitate the use of [tmdb](https://developer.themoviedb.org/reference/intro/getting-started) API
  * Download the poster to save in a temp directory
  * create an API to save in the data frame and use that data frame.
  * Create [populate.py](https://github.com/Avijeetas/movieflix/blob/main/movie-poster/populate.py)https://github.com/Avijeetas/movieflix/blob/main/movie-poster/populate.py  to save call the microservice to save the movie info to mysql
  * 
 
