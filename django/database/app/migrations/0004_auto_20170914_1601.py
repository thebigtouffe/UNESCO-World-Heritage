# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-09-14 16:01
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('app', '0003_auto_20170914_0849'),
    ]

    operations = [
        migrations.RenameField(
            model_name='site',
            old_name='image1',
            new_name='image1_url',
        ),
        migrations.RenameField(
            model_name='site',
            old_name='image2',
            new_name='image2_url',
        ),
        migrations.RenameField(
            model_name='site',
            old_name='image3',
            new_name='image3_url',
        ),
        migrations.AddField(
            model_name='site',
            name='image1_description',
            field=models.TextField(default=''),
        ),
        migrations.AddField(
            model_name='site',
            name='image2_description',
            field=models.TextField(default=''),
        ),
        migrations.AddField(
            model_name='site',
            name='image3_description',
            field=models.TextField(default=''),
        ),
        migrations.AddField(
            model_name='site',
            name='url',
            field=models.TextField(default=''),
        ),
    ]
