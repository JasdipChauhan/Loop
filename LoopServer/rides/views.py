from django.shortcuts import render
from django.shortcuts import HttpResponse

from .models import Ride

# Create your views here.
def ride_list(request):
    rides = Ride.objects.all()
    return HttpResponse(rides)

