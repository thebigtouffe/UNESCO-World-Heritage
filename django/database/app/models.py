from django.db import models


class Zone(models.Model):
	name = models.CharField(max_length=200, unique=True)
	name_fr = models.CharField(max_length=200, default="")

	def __str__(self):
		return self.name


class Country(models.Model):
	name = models.CharField(max_length=200)
	name_fr = models.CharField(max_length=200)
	iso = models.CharField(max_length=5, unique=True)

	searchable_name = models.CharField(max_length=200, default="")
	searchable_name_fr = models.CharField(max_length=200, default="")

	def __str__(self):
		return self.name


class Category(models.Model):
	name = models.CharField(unique=True, max_length=200)
	name_fr = models.CharField(max_length=200)

	def __str__(self):
		return self.name


class Criterion(models.Model):
	number = models.IntegerField(primary_key=True)
	description = models.CharField(max_length=200)
	description_fr = models.CharField(max_length=200)

	def __str__(self):
		return self.number


class Site(models.Model):
	number = models.IntegerField(primary_key=True)
	name = models.CharField(max_length=200, null=True, blank=True)
	name_fr = models.CharField(max_length=200, null=True, blank=True)
	category = models.ForeignKey(Category, on_delete=models.CASCADE, null=True, blank=True)
	country = models.ManyToManyField(Country)
	zone = models.ForeignKey(Zone, on_delete=models.CASCADE, null=True, blank=True)
	criteria = models.ManyToManyField(Criterion)
	year_inscribed = models.IntegerField(default=0)
	endangered = models.BooleanField(default=False)

	latitude = models.FloatField(null=True, blank=True)
	longitude = models.FloatField(null=True, blank=True)

	long_description = models.TextField(default="")
	long_description_fr = models.TextField(default="")
	short_description = models.TextField(default="")
	short_description_fr = models.TextField(default="")
	justification = models.TextField(default="")
	justification_fr = models.TextField(default="")
	historical_description = models.TextField(default="")
	historical_description_fr = models.TextField(default="")

	thumb = models.BinaryField(default=b"")
	url = models.TextField(default="")
	image1_url = models.TextField(default="")
	image1_description = models.TextField(default="")
	image2_url = models.TextField(default="")
	image2_description = models.TextField(default="")
	image3_url = models.TextField(default="")
	image3_description = models.TextField(default="")

	searchable_name = models.CharField(max_length=200, default="")
	searchable_name_fr = models.CharField(max_length=200, default="")

	def __str__(self):
		return self.name

