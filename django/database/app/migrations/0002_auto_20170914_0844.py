# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-09-14 08:44
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('app', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='site',
            name='image1',
            field=models.TextField(default=''),
        ),
        migrations.AddField(
            model_name='site',
            name='image2',
            field=models.TextField(default=''),
        ),
        migrations.AddField(
            model_name='site',
            name='image3',
            field=models.TextField(default=''),
        ),
        migrations.AddField(
            model_name='site',
            name='thumb',
            field=models.TextField(default=''),
        ),
    ]