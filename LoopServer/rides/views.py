from django.shortcuts import render
from django.shortcuts import HttpResponse
from rest_framework.views import APIView
from rest_framework.response import Response

from . import models
from . import serializers

# Create your views here.
class ListRides(APIView):
    def get(self, request, format=None):
        rides = models.Ride.objects.all()
        serialized = serializers.RideSerializer(rides, many=True)
        return Response(serialized.data)