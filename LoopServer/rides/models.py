from django.db import models


#Models

# Create your models here.
class User(models.Model):
    first_name = models.CharField(max_length=255)
    last_name = models.CharField(max_length=255)
    username = models.CharField(max_length=255)
    password = models.CharField(max_length=255)
    email_address = models.EmailField()
    phone_number = models.CharField(max_length=10)

class Ride(models.Model):
    driver_name = models.CharField(max_length=255)
    driver_email = models.EmailField()
    pickup = models.CharField(max_length=255)
    destination = models.CharField(max_length=255)
    created = models.DateTimeField(auto_now_add=True)

    class Meta:
        ordering = ('created',)

    def __str__(self):
        return self.driver_name
