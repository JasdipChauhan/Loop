# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-10-14 01:43
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('rides', '0004_ride_time'),
    ]

    operations = [
        migrations.AddField(
            model_name='ride',
            name='pickupLocation',
            field=models.CharField(default='location', max_length=255),
            preserve_default=False,
        ),
    ]
