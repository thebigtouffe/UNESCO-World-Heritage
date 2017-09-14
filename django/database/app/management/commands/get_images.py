# -*- coding: utf-8 -*-

from django.core.management.base import BaseCommand
from app.models import *

import urllib
import requests
from lxml import html

def format(text):
    text = text.replace("\t","").replace("\r","").replace("\n","")
    return text


class Command(BaseCommand):
    help = 'Get images'

    def handle(self, *args, **options):
        UNESCO_BASE_URL = "http://whc.unesco.org"

        sites = Site.objects.all()
        for i, s in enumerate(sites):
            page = html.fromstring(urllib.request.urlopen(s.url).read())

            for img in page.xpath("//img"):
                image_url = img.get("data-src")
                style = img.get("style")

                if image_url:
                    if "site" in image_url and 'width:100%' in style:

                        strong_tags = page.xpath("//strong")
                        description = ""
                        for st in strong_tags:
                            if st.get("class"):
                                if "description" in st.get("class"):
                                    description = st.text
                                    break
                        
                        try:
                            description = format(description)
                            description = description[description.index("©"):]
                        except:
                            description = ""

                        image_url = UNESCO_BASE_URL + image_url

                        if s.image1_url == "":
                            s.image1_url = image_url
                            s.image1_description = description
                        elif s.image2_url == "":
                            s.image2_url = image_url
                            s.image2_description = description
                        elif s.image3_url == "":
                            s.image3_url = image_url
                            s.image3_description = description

            print(i)
            s.save()

                
            