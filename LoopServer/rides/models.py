from django.db import models

# Create your models here.

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
