from django.db import models


class Zone(models.Model):
	name = models.CharField(max_length=200, unique=True)
	name_fr = models.CharField(max_length=200, default="")


class Country(models.Model):
	name = models.CharField(max_length=200)
	name_fr = models.CharField(max_length=200)
	iso = models.CharField(max_length=5, unique=True)


class Category(models.Model):
	name = models.CharField(unique=True, max_length=200)
	name_fr = models.CharField(max_length=200)


class Criterion(models.Model):
	number = models.IntegerField(primary_key=True)
	description = models.CharField(max_length=200)
	description_fr = models.CharField(max_length=200)


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

