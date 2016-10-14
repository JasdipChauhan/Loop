from rest_framework import serializers
from .models import Ride, Rider
from django.contrib.auth.models import User

class UserSerializer(serializers.ModelSerializer):
    rides = serializers.PrimaryKeyRelatedField(many=True, queryset=Ride.objects.all())

    class Meta:
        model = User
        fields = ('id', 'username', 'rides')


class RiderSerializer(serializers.ModelSerializer):
    owner = serializers.ReadOnlyField(source='owner.username')

    class Meta:
        fields = (
            'owner',
            'id',
            'first_name',
            'last_name',
            'username',
            'password',
            'email_address',
            'phone_number',
        )

        model = Rider

class RideSerializer(serializers.ModelSerializer):
    class Meta:
        fields = (
            'id',
            'driver_name',
            'driver_email',
            'driver_phone_number',
            'pickup',
            'destination',
            'time',
            'seats_left',
            'created',
        )

        model = Ride
