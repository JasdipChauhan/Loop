# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-10-14 18:46
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('rides', '0008_ride_date'),
    ]

    operations = [
        migrations.RenameField(
            model_name='ride',
            old_name='destination',
            new_name='dropoff',
        ),
    ]
