from rest_framework import generics
from rest_framework import permissions
from django.http import HttpResponse
from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser
from django.views.decorators.csrf import csrf_exempt

from .models import *
from .serializers import *

#class isSuperUser(permissions.BasePermission):
#    def has_permission(self, request, view):
#        if request.method == 'DELETE' and request.user.is_superuser:
#            return True
#        return False
#
## User Views
#class ListCreateUser(generics.ListCreateAPIView):
#    #permission_classes = (
#     ##  permissions.DjangoModelPermissions,)
#
#    queryset = models.User.objects.all() #send out the data
#    serializer_class = serializers.UserSerializer
#
#    def get_queryset(self):
#        return self.queryset.filter(username=self.kwargs.get('users_pk'))
#
#
#class RetrieveUpdateDeleteUser(generics.RetrieveUpdateDestroyAPIView):
#    queryset = models.User.objects.all() #find the object you look for
#    serializer_class = serializers.UserSerializer
#
## Ride Views
#class ListCreateRide(generics.ListCreateAPIView):
#    #permission_classes = (
#      #  isSuperUser,
#     #   permissions.DjangoModelPermissions,)
#
#    queryset = models.Ride.objects.all() #send out the data
#    serializer_class = serializers.RideSerializer
#
#class RetrieveUpdateDeleteRide(generics.RetrieveUpdateDestroyAPIView):
#    queryset = models.Ride.objects.all() #find the object you look for
#    serializer_class = serializers.RideSerializer

class JSONResponse(HttpResponse):
    def __init__(self, data, **kwargs):
        content = JSONRenderer().render(data)
        kwargs['content_type'] = 'application/json'
        super(JSONResponse, self).__init__(content, **kwargs)


@csrf_exempt
def rides_list(request):
    if request.method == 'GET':
        rides = Ride.objects.all()
        serializer = RideSerializer(rides, many=True)
        return JSONResponse(serializer.data)

    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = RideSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return JSONResponse(serializer.data, status=201)
        return JSONResponse(serializer.errors, status=400)

@csrf_exempt
def ride_detail(request, pk):
    """
    Retrieve, update or delete a code snippet.
    """
    try:
        ride = Ride.objects.get(pk=pk)
    except Ride.DoesNotExist:
        return HttpResponse(status=404)

    if request.method == 'GET':
        serializer = RideSerializer(ride)
        return JSONResponse(serializer.data)

    elif request.method == 'PUT':
        data = JSONParser().parse(request)
        serializer = RideSerializer(ride, data=data)
        if serializer.is_valid():
            serializer.save()
            return JSONResponse(serializer.data)
        return JSONResponse(serializer.errors, status=400)

    elif request.method == 'DELETE':
        ride.delete()
        return HttpResponse(status=204)