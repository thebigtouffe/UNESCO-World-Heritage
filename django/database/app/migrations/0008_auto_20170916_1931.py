# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-09-16 19:31
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('app', '0007_auto_20170916_1827'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='user',
            name='disliked_sites',
        ),
        migrations.RemoveField(
            model_name='user',
            name='liked_sites',
        ),
        migrations.RemoveField(
            model_name='user',
            name='seen_sites',
        ),
        migrations.DeleteModel(
            name='User',
        ),
    ]