from django.core.management.base import BaseCommand
from app.models import *

import csv

class Command(BaseCommand):
	help = 'Populate Django database with base information (countries, zone, ...)'

	def handle(self, *args, **options):
		print("Populating country database")
		country_database = open('country_codes.csv', 'rt', encoding='utf8')
		countries = csv.reader(country_database, delimiter=',', quotechar='"')
		for country in countries:
			print(country[5])
			try:
				Country.objects.create(iso=country[2],name=country[5],name_fr=country[4])
			except Exception as e:
				print("Unable to add country to database")
				print(e)
			print(country)

		try:
			print("Populating zone database")
			Zone.objects.create(name="Africa", name_fr="Afrique")
			Zone.objects.create(name="Asia and the Pacific", name_fr="Asie et pacifique")
			Zone.objects.create(name="Europe and North America", name_fr="Europe et Amérique du nord")
			Zone.objects.create(name="Latin America and the Caribbean", name_fr="Amérique latine et Caraïbes")
			Zone.objects.create(name="Arab States", name_fr="États arabes")
		except Exception as e:
				print("Unable to add zones to database")
				print(e)

		try:
			print("Populating category database")
			Category.objects.create(name="Cultural", name_fr="Culturel")
			Category.objects.create(name="Mixed", name_fr="Mixte")
			Category.objects.create(name="Natural", name_fr="Naturel")
		except Exception as e:
				print("Unable to add categories to database")
				print(e)

		try:
			print("Populating criterion")
			Criterion.objects.create(number=1, description="Represents a masterpiece of human creative genius and cultural significance.", description_fr="Représenter un chef-d'œuvre du génie créateur humain.")
			Criterion.objects.create(number=2, description="Exhibits an important interchange of human values, over a span of time, or within a cultural area of the world, on developments in architecture or technology, monumental arts, town-planning, or landscape design.", description_fr="Témoigner d'un échange d'influences considérable pendant une période donnée ou dans une aire culturelle déterminée, sur le développement de l'architecture ou de la technologie, des arts monumentaux, de la planification des villes ou de la création de paysages.")
			Criterion.objects.create(number=3, description="To bear a unique or at least exceptional testimony to a cultural tradition or to a civilization which is living or which has disappeared.", description_fr="Apporter un témoignage unique ou du moins exceptionnel sur une tradition culturelle ou une civilisation vivante ou disparue.")
			Criterion.objects.create(number=4, description="Is an outstanding example of a type of building, architectural, or technological ensemble or landscape which illustrates a significant stage in human history.", description_fr="Offrir un exemple éminent d'un type de construction ou d'ensemble architectural ou technologique ou de paysage illustrant une ou des périodes significative(s) de l'histoire humaine.")
			Criterion.objects.create(number=5, description="Is an outstanding example of a traditional human settlement, land-use, or sea-use which is representative of a culture, or human interaction with the environment especially when it has become vulnerable under the impact of irreversible change.", description_fr="Être un exemple éminent d'établissement humain traditionnel, de l'utilisation traditionnelle du territoire ou de la mer, qui soit représentatif d'une culture (ou de cultures), ou de l'interaction humaine avec l'environnement, spécialement quand celui-ci est devenu vulnérable sous l'impact d'une mutation irréversible.")
			Criterion.objects.create(number=6, description="Is directly or tangibly associated with events or living traditions, with ideas, or with beliefs, with artistic and literary works of outstanding universal significance.", description_fr="Être directement ou matériellement associé à des événements ou des traditions vivantes, des idées, des croyances ou des œuvres artistiques et littéraires ayant une signification universelle exceptionnelle (Le Comité considère que ce critère doit préférablement être utilisé en conjonction avec d'autres critères).")
			Criterion.objects.create(number=7, description="Contains superlative natural phenomena or areas of exceptional natural beauty and aesthetic importance.", description_fr="Représenter des phénomènes naturels ou des aires d'une beauté naturelle et d'une importance esthétique exceptionnelles")
			Criterion.objects.create(number=8, description="Is an outstanding example representing major stages of Earth's history, including the record of life, significant on-going geological processes in the development of landforms, or significant geomorphic or physiographic features.", description_fr="Être des exemples éminemment représentatifs des grands stades de l'histoire de la terre, y compris le témoignage de la vie, de processus géologiques en cours dans le développement des formes terrestres ou d'éléments géomorphiques ou physiographiques ayant une grande signification.")
			Criterion.objects.create(number=9, description="Is an outstanding example representing significant on-going ecological and biological processes in the evolution and development of terrestrial, fresh water, coastal and marine ecosystems, and communities of plants and animals.", description_fr="Être des exemples éminemment représentatifs de processus écologiques et biologiques en cours dans l'évolution et le développement des écosystèmes et communautés de plantes et d'animaux terrestres, aquatiques, côtiers et marins.")
			Criterion.objects.create(number=10, description="Contains the most important and significant natural habitats for in-situ conservation of biological diversity, including those containing threatened species of outstanding universal value from the point of view of science or conservation.", description_fr="Contenir les habitats naturels les plus représentatifs et les plus importants pour la conservation in situ de la diversité biologique, y compris ceux où survivent des espèces menacées ayant une valeur universelle exceptionnelle du point de vue de la science ou de la conservation.")
		except Exception as e:
			print("Unable to add criteria to database")
			print(e)


		