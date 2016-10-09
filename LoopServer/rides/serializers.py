from rest_framework import serializers
from . import models

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        fields = (
            'id',
            'first_name',
            'last_name',
            'username',
            'password',
            'email_address',
            'phone_number',
        )

        model = models.User

class RiderSerializer(serializers.ModelSerializer):
    class Meta:
        fields = (
            'id',
            'first_name',
            'last_name',
            'username',
            'password',
            'email_address',
            'phone_number',
        )

        model = models.Rider

class RideSerializer(serializers.ModelSerializer):
    class Meta:
        fields = (
            'id',
            'driver_name',
            'driver_email',
            'driver_phone_number',
            'pickup',
            'destination',
            'seats_left',
            'created',
        )

        model = models.Ride
