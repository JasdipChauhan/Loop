from rest_framework import serializers
from . import models

class RideSerializer(serializers.ModelSerializer):
    class Meta:

        fields = (
            'driver_name',
            'driver_email',
            'pickup',
            'destination',
            'created',
        )

        model = models.Ride