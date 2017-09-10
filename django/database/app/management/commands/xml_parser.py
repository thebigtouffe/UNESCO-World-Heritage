# -*- coding: utf-8 -*-

from django.core.management.base import BaseCommand
from app.models import *

from lxml import etree


class Command(BaseCommand):
    help = 'Populate Django database with XML data'

    def handle(self, *args, **options):
        print("This tool will populate Django database with XML data")

        tree = etree.parse("database_en.xml")
        print("English database opened.")
        for site in tree.xpath("/query/row"):
            number = int(site.xpath("unique_number")[0].text)
            print(number)

            s, c = Site.objects.get_or_create(number=number)

            category = site.xpath("category")[0].text
            s.category = Category.objects.get(name=category)

            s.name = site.xpath("site")[0].text
            s.year_inscribed = int(site.xpath("date_inscribed")[0].text)

            s.endangered = site.xpath("danger")[0].text is not None

            latitude = site.xpath("latitude")[0].text
            longitude = site.xpath("longitude")[0].text
            if (latitude is not None):
                s.latitude = float(latitude)
                s.longitude = float(longitude)

            long_description = site.xpath("long_description")[0].text
            short_description = site.xpath("short_description")[0].text
            justification = site.xpath("justification")[0].text
            historical_description = site.xpath("historical_description")[0].text

            if long_description:
                s.long_description = long_description
            if short_description:
                s.short_description = short_description
            if justification:
                s.justification = justification
            if historical_description:
                s.historical_description = historical_description

            s.save()
            # danger = site.get("danger") is not None
            # print(number)
            # print(site.get("unique_number"))






# # Refresh genres
# all_genres = get_all_genres()

# def analyse_movie(title, year, file_id, server):
#     if year is None:
#         search.movie(query=title, language='fr')
#     else:
#         search.movie(query=title, year=year, language='fr')

#     if len(search.results) > 0:
#         result = search.results[0]
#         id = result.get("id")
#         movie, created = Movie.objects.get_or_create(id=id)
#         result_detailed = tmdb.Movies(id=id).info(language='fr')
#         credits = tmdb.Movies(id=id).credits()

#         if created:
#             movie.originalTitle = result.get("original_title")
#             movie.title = result.get("title")
#             movie.is_anime = is_anime(result)

#             print("Retrieving data about %s" % movie.title)

#             try:
#                 movie.duration = int(result_detailed["runtime"])
#             except:
#                 movie.duration = 0

#             try:
#                 movie.year = int(result["release_date"][0:4])
#             except:
#                 movie.year = 0

#             for c in credits['crew']:
#                 if c['job'].lower() == "director":
#                     director, director_created = Person.objects.get_or_create(id=c['id'])

#                     if director_created:
#                         valid_data = get_person_information(director, c)
#                     else:
#                         valid_data = director.name is not None

#                     if valid_data:
#                         movie.director.add(director)

#             for c in credits['cast']:
#                 order = c['order']

#                 # We keep only the X most important actors
#                 if order < settings.number_of_actors_to_fetch:
#                     actor, actor_created = Person.objects.get_or_create(id=c['id'])

#                     if actor_created:
#                         valid_data = get_person_information(actor, c)
#                     else:
#                         valid_data = actor.name is not None

#                     if valid_data:
#                         movie_actor, movie_actor_created = movieActor.objects.get_or_create(
#                             actor=actor, movie=movie, order=order)
#                         movie.actor.add(movie_actor.actor)
#                 else:
#                     break

#             try:
#                 poster = result.get("poster_path")
#                 movie.picture_url = picture_base_url + poster
#             except:
#                 pass

#             genres = result.get("genre_ids")
#             for g in genres:
#                 movie.genre.add(all_genres[str(g)])

#         # Add file-movie mapping
#         file, file_created = File.objects.get_or_create(id=file_id, server=server)
#         movie.file.add(file)

#         movie.save()
        
