from rest_framework import generics
from rest_framework import permissions

from . import models
from . import serializers

class isSuperUser(permissions.BasePermission):
    def has_permission(self, request, view):
        if request.method == 'DELETE' and request.user.is_superuser:
            return True
        return False

# Create your views here.
class ListCreateRides(generics.ListCreateAPIView):
    permission_classes = (
        isSuperUser,
        permissions.DjangoModelPermissions,)

    queryset = models.Ride.objects.all() #send out the data
    serializer_class = serializers.RideSerializer

class RetrieveUpdateDeleteRide(generics.RetrieveUpdateDestroyAPIView):
    queryset = models.Ride.objects.all() #find the object you look for
    serializer_class = serializers.RideSerializer