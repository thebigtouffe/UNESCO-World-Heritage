# -*- coding: utf-8 -*-

from django.core.management.base import BaseCommand
from app.models import *

from lxml import etree
import roman
import re
import base64
import requests


def removeHTMLTags(raw_html):
        cleanr = re.compile('<.*?>')
        cleantext = re.sub(cleanr, '', raw_html)
        return cleantext


def formatDescription(text):
    text = text.replace(" ,", ",")
    text = text.replace(" .", ".")
    text = text.replace(" )", ")")
    text = text.replace("( ", "(")
    return text


class Command(BaseCommand):
    help = 'Populate Django database with XML data'

    def handle(self, *args, **options):
        print("This tool will populate Django database with XML data")

        tree = etree.parse("database_en.xml")
        print("English database opened. \n")
        english_data = tree.xpath("/query/row")

        for i, site in enumerate(english_data):
            number = int(site.xpath("id_number")[0].text)
            print("%s / %s" % (str(i+1), str(len(english_data))))

            s, c = Site.objects.get_or_create(number=number)

            category = site.xpath("category")[0].text
            s.category = Category.objects.get(name=category)

            s.name = removeHTMLTags(site.xpath("site")[0].text)
            s.year_inscribed = int(site.xpath("date_inscribed")[0].text)

            s.endangered = site.xpath("danger")[0].text is not None

            criteria = site.xpath("criteria_txt")[0].text.replace("(","").split(")")[:-1]
            for criterion in criteria:
                s.criteria.add(Criterion.objects.get(number=roman.fromRoman(criterion.upper())))

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
                s.long_description = formatDescription(long_description)
            if short_description:
                s.short_description = formatDescription(short_description)
            if justification:
                s.justification = formatDescription(justification)
            if historical_description:
                s.historical_description = formatDescription(historical_description)

            try:
                countries = site.xpath("iso_code")[0].text.split(",")
                for country in countries:
                    db_country = Country.objects.get(iso=country.upper())
                    s.country.add(db_country)
            except Exception as e:
                print(e)

            zone = site.xpath("region")[0].text
            s.zone = Zone.objects.get(name=zone)

            # Get thumb
            image_url = site.xpath("image_url")[0].text
            s.thumb = base64.b64encode(requests.get(image_url).content)

            s.url = site.xpath("http_url")[0].text

            s.save()


        tree_fr = etree.parse("database_fr.xml")
        print("French database opened.")
        print("Adding French data...")
        for site in tree_fr.xpath("/query/row"):
            number = int(site.xpath("id_number")[0].text)

            s, c = Site.objects.get_or_create(number=number)

            s.name_fr = removeHTMLTags(site.xpath("site")[0].text)
            
            long_description_fr = site.xpath("long_description")[0].text
            short_description_fr = site.xpath("short_description")[0].text
            justification_fr = site.xpath("justification")[0].text
            historical_description_fr = site.xpath("historical_description")[0].text

            if long_description_fr:
                s.long_description_fr = formatDescription(long_description_fr)
            if short_description_fr:
                s.short_description_fr = formatDescription(short_description_fr)
            if justification_fr:
                s.justification_fr = formatDescription(justification_fr)
            if historical_description_fr:
                s.historical_description_fr = formatDescription(historical_description_fr)

            s.save()

