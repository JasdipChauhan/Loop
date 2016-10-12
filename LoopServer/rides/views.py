from rest_framework import generics
from rest_framework import permissions
from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser
from rest_framework.response import Response
from django.views.decorators.csrf import csrf_exempt
from rest_framework.decorators import api_view
from rest_framework import status
from rest_framework.views import APIView
from django.http import Http404


from .models import *
from .serializers import *

class RideList(APIView):
    """
    List all rides, or create a new ride
    """
    def get(self, request, format=None):
        rides = Ride.objects.all()
        rideSerializer = RideSerializer(rides, many=True)
        return Response(rideSerializer.data)

    def post(self, request, format=None):
        rideSerializer = RideSerializer(data=request.data)
        if rideSerializer.is_valid():
            rideSerializer.save()
            return Response(rideSerializer.data, status=status.HTTP_201_CREATED)
        return Response(rideSerializer.errors, status=status.HTTP_400_BAD_REQUEST)


class RideDetail(APIView):
    """
    Retrieve, update or delete a ride instance.
    """
    def get_object(self, pk):
        try:
            return Ride.objects.get(pk=pk)
        except Ride.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        ride = self.get_object(pk)
        serializer = RideSerializer(ride)
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        ride = self.get_object(pk)
        serializer = RideSerializer(ride, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        ride = self.get_object(pk)
        ride.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
