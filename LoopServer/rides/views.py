from rest_framework import generics
from django.shortcuts import render
from django.shortcuts import HttpResponse
from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response

from . import models
from . import serializers

# Create your views here.
class ListCreateRides(generics.ListCreateAPIView):
    queryset = models.Ride.objects.all() #send out the data
    serializer_class = serializers.RideSerializer

class RetrieveUpdateDeleteRide(generics.RetrieveUpdateDestroyAPIView):
    queryset = models.Ride.objects.all() #find the object you look for
    serializer_class = serializers.RideSerializer