# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-09-18 16:27
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('app', '0008_auto_20170916_1931'),
    ]

    operations = [
        migrations.AddField(
            model_name='country',
            name='searchable_name',
            field=models.CharField(blank=True, max_length=200, null=True),
        ),
        migrations.AddField(
            model_name='country',
            name='searchable_name_fr',
            field=models.CharField(blank=True, max_length=200, null=True),
        ),
        migrations.AddField(
            model_name='site',
            name='searchable_name',
            field=models.CharField(blank=True, max_length=200, null=True),
        ),
        migrations.AddField(
            model_name='site',
            name='searchable_name_fr',
            field=models.CharField(blank=True, max_length=200, null=True),
        ),
    ]
