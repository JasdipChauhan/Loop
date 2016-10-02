from rest_framework import serializers
from . import models

class RideSerializer(serializers.ModelSerializer):
    class Meta:

        fields = (
            'id',
            'driver_name',
            'driver_email',
            'pickup',
            'destination',
            'created',
        )

        model = models.Ride