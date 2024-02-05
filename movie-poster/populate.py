import pandas as pd
import requests
from typing import List, Dict

class MovieDto:
    def __init__(self, title, directors, countries, movie_cast, genre, description, release_year, duration, poster):
        self.title = title
        self.directors = directors
        self.countries = countries
        self.movie_cast = movie_cast
        self.genre = genre
        self.description = description
        self.release_year = release_year
        self.duration = duration
        self.poster = poster
       
# Read DataFrame from CSV file (Replace 'your_data.csv' with the actual file path)
df = pd.read_csv('archive/filmtv_movies_with_poster_name.csv')

# Function to convert DataFrame row to MovieDto object
def row_to_movie_dto(row):
    return MovieDto(
        title=row['title'],
        directors=set(row['directors'].split(',')),
        countries=set(row['country'].split(',')),
        movie_cast = set(row['actors'].split(',')) if pd.notna(row['actors']) else set(),
        genre=set(row['genre'].split(',')),
        description=row['description'],
        release_year=row['year'],
        duration=row['duration'],
        poster=row['file_name'],
    )
# Convert DataFrame to a list of MovieDto objects
movie_dtos = df.apply(row_to_movie_dto, axis=1).tolist()


# Serialize MovieDto objects to JSON string

for movie_dto in movie_dtos:
    print(movie_dto.genre)
    json_data=({
        "title": movie_dto.title,
        "directors": list(movie_dto.directors),
        "countries": list(movie_dto.countries),
        "movieCast": list(movie_dto.movie_cast),
        "genre": list(movie_dto.genre),
        "description": movie_dto.description,
        "releaseYear": movie_dto.release_year,
        "duration": movie_dto.duration,
        "poster": movie_dto.poster[1:] if pd.notna(movie_dto.poster) else "",
        "isDeleted": False
    })

    url = 'http://127.0.0.1:8080/api/v1/movies/populate-movies'
    headers = {'Content-Type': 'application/json'}
    response = requests.post(url, json=json_data, headers=headers)

    # Print the response from the Spring Boot backend
    print(response.text)


