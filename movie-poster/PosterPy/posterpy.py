#!/usr/bin/env python3
# coded by sameera madushan
# modified by Avijeet Shil
import logging
import os
import shutil
from tkinter import Tk, filedialog
import requests
from PIL import Image, ImageOps
from tqdm import tqdm
import imdb
import questionary

def api_key():

    SAMPLE_URL = 'https://api.themoviedb.org/3/movie/76341?api_key={0}'
    try:
        while True:
            if os.path.exists("api.txt") and os.path.getsize("api.txt") > 0:
                f = open("api.txt", "r")
                key = f.read()
                req = requests.get(SAMPLE_URL.format(key))
                if req.status_code == 200:
                    print("\nAPI Key authentication successful.\n")
                    return key
                else:
                    pass

            print("\nTo register for an API key, Visit: https://www.themoviedb.org/account/signup")
            get_key = input("API key required. Please enter the API key: ")
            req = requests.get(SAMPLE_URL.format(get_key))
            if req.status_code == 200:
                f = open("api.txt", "w")
                f.write(get_key)
                f.close()
            else:
                print("\nInvalid API key: You must be granted a valid key.")
    except OSError:
        print("\nUnknown Error")

def search_movie(title, year):

    try:
        ia = imdb.IMDb()
       
        movies = ia.search_movie(title)

        choices_list = []
        
        for i in movies:
            get_title = i['title']
            get_id = i.movieID
            try:
                get_year = i['year']
               # print(get_year, ' ', ' ', year,' ', str(get_year) == str(year))
                if str(get_year) == str(year):
                #    p = ("{: <10}".format(str(get_id))+ get_title + " " + "(" + str(get_year) + ")")
                    choices_list.append(get_id)

            except KeyError:
                pass
            
       # movie_list = questionary.select("Oh! there's a lot. What did you mean? ", choices=choices_list).unsafe_ask()
        
       
        
        selected_id = choices_list[0]
        return selected_id
        
    except KeyboardInterrupt:
        logging.info("Keyboard Interrupted")
        quit()
    except AttributeError:
        quit()
    except ValueError:
        logging.info("Unknown movie name.")
        quit()

def get_image_url(req):

    for k, v in req.items():
        for i in v:
            for k, v in i.items():
                if v is None:
                    print("k for none v ", k)
                if k == 'poster_path' and v is not None:
                    image_url = 'http://image.tmdb.org/t/p/w500/' + v
                    return [image_url, v]

def generate_movie_poster(title, year):
    try:
            
        URL = 'https://api.themoviedb.org/3/find/tt{0}?api_key={1}&language=en-US&external_source=imdb_id'.format(search_movie(title, year), api_key())

        req = requests.get(URL).json()
        returning_list = get_image_url(req)
        if returning_list is None:
            logging.info("\nNo poster found with title ", title)
            return None            
        else:
            return download_poster(returning_list, title)
    except:
        pass
def download_poster(returning_list, title):
    
        url = returning_list[0]
        filename = returning_list[1]
        _response = requests.get(url).content
        file_size_request = requests.get(url, stream=True)
        file_size = int(file_size_request.headers['Content-Length'])
        block_size = 1024
        t = tqdm(total=file_size, unit='B', unit_scale=True, desc="Downloading", ascii=True)
        with open("../posters/"+filename[1:], 'wb') as f:
            for data in file_size_request.iter_content(block_size):
                t.update(len(data))
                f.write(data)
        t.close()
        logging.info("\nPoster downloaded successfully with title ", title)
        return filename



def tk_get_file_path():

    
    posters_directory = "posters"
    if not os.path.exists(posters_directory):
        os.makedirs(posters_directory)
    return posters_directory



print(generate_movie_poster('Invitación a un Asesinato', '2023'))

