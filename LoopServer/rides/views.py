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
from rest_framework import mixins



from .models import *
from .serializers import *

class RideList(generics.ListCreateAPIView):
    """
    List all rides, or create a new ride
    """
    queryset = Ride.objects.all()
    serializer_class = RideSerializer

class RideDetail(generics.RetrieveUpdateDestroyAPIView):
    """
    Retrieve, update or delete a ride instance.
    """
    queryset = Ride.objects.all()
    serializer_class = RideSerializer
