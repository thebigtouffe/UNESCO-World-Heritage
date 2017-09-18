# -*- coding: utf-8 -*-

from django.core.management.base import BaseCommand
from app.models import *

import unidecode

def removeAccent(text):
    unidecode.unidecode(text)


class Command(BaseCommand):
    help = 'Create searchable elements'

    def handle(self, *args, **options):

        countries = Country.objects.all()
        sites = Site.objects.all()

        print("Creating searchable countries")
        for i, country in enumerate(countries):
            print(str(i) + "/" + str(len(countries)))

            if (len(country.searchable_name) == 0):
                country.searchable_name = unidecode.unidecode(country.name).lower()
                country.searchable_name_fr = unidecode.unidecode(country.name_fr).lower()
                country.save()


        print("Creating searchable sites")
        for i, site in enumerate(sites):
            print(str(i) + "/" + str(len(sites)))

            if (len(site.searchable_name) == 0):
                site.searchable_name = unidecode.unidecode(site.name).lower()
                site.searchable_name_fr = unidecode.unidecode(site.name_fr).lower()
                site.save()


            