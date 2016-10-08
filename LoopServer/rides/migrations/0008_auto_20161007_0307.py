# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-10-07 03:07
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('rides', '0007_auto_20161007_0305'),
    ]

    operations = [
        migrations.CreateModel(
            name='Riders',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('first_name', models.CharField(max_length=255)),
                ('last_name', models.CharField(max_length=255)),
                ('username', models.CharField(max_length=255)),
                ('password', models.CharField(max_length=255)),
                ('email_address', models.EmailField(max_length=254)),
                ('phone_number', models.CharField(max_length=10)),
            ],
            options={
                'ordering': ('username',),
            },
        ),
        migrations.AlterField(
            model_name='ride',
            name='seats_filled',
            field=models.IntegerField(default=0),
        ),
    ]
