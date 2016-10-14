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
from rest_framework import permissions

from django.contrib.auth.models import User

from .permissions import IsOwnerOrReadOnly
from .models import Ride
from .serializers import RideSerializer, UserSerializer
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework.reverse import reverse
from rest_framework import renderers

#######
#Rides#
#######

class RideList(generics.ListCreateAPIView):
    """
    List all rides, or create a new ride
    """
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,)
    queryset = Ride.objects.all()
    serializer_class = RideSerializer

    def perform_create(self, serializer):
        serializer.save(owner=self.request.user)

class RideDetail(generics.RetrieveUpdateDestroyAPIView):
    """
    Retrieve, update or delete a ride instance.
    """
    permission_classes = (permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly)
    queryset = Ride.objects.all()
    serializer_class = RideSerializer

#######
#Users#
#######

class UserList(generics.ListAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer

class UserDetail(generics.RetrieveAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer
