import os
import logging
from flask import Flask, render_template, request, send_from_directory
import time
import pandas as pd
from PosterPy import posterpy

app = Flask(__name__)

app.debug = True

@app.get('/')
def home_get():

    return render_template('index.html')

    

@app.route('/favicon.ico')
def favicon():
    return send_from_directory(os.path.join(app.root_path, 'static'),
                               'favicon.ico')


@app.get("/download")
def download_poster():
    df = read_csv()
    return "ok"
def generate_poster(row):
    time.sleep(1) 
    print("Done  with  ", row['title'])
    return posterpy.generate_movie_poster(row['title'], row['year'])

def read_csv():
    df = pd.read_csv("archive/filmtv_movies.csv")
    modified_df = df[['title','year','genre','duration','country','directors','actors','description']]
    #modified_df = modified_df.sort_values(by='year', ascending=False)
    filtered_df = modified_df[modified_df['year']>=2023 ]
    filtered_df['file_name'] = filtered_df.apply(generate_poster, axis=1)
    filtered_df.to_csv("archive/filmtv_movies_with_poster_name.csv")
    return filtered_df
read_csv()