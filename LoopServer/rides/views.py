from django.shortcuts import render
from django.shortcuts import HttpResponse
from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response

from . import models
from . import serializers

# Create your views here.
class ListCreateRides(APIView):
    def get(self, request, format=None):
        rides = models.Ride.objects.all()
        serialized = serializers.RideSerializer(rides, many=True)
        return Response(serialized.data)

    def post(self, request, format=None):
        serialized = serializers.RideSerializer(data=request.data)
        serialized.is_valid(raise_exception=True)
        serialized.save() #when saving to database, serialized will be updated with all the fields from the model
        return Response(serialized.data, status=status.HTTP_201_CREATED)