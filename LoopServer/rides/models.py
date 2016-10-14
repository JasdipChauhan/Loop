from django.db import models
import datetime

#Models

# Create your models here.

class Rider(models.Model):
    first_name = models.CharField(max_length=255)
    last_name = models.CharField(max_length=255)
    username = models.CharField(max_length=255)
    password = models.CharField(max_length=255)
    email_address = models.EmailField()
    phone_number = models.CharField(max_length=10)

    class Meta:
        ordering = ('username',)

    def __str__(self):
        return self.username


class Ride(models.Model):
    owner = models.ForeignKey('auth.User', related_name='rides')
    driver_name = models.CharField(max_length=255)
    driver_email = models.EmailField()
    driver_phone_number = models.CharField(max_length=10)
    pickup = models.CharField(max_length=255)
    dropoff = models.CharField(max_length=255)
    date = models.DateField(default=datetime.datetime.now)
    time = models.CharField(max_length=255)
    seats_left = models.IntegerField()
    price = models.DecimalField(decimal_places=2, max_digits=9)
    created = models.DateTimeField(auto_now_add=True)

    class Meta:
        ordering = ('created',)

    def __str__(self):
        return self.driver_name