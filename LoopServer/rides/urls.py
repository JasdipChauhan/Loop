from django.conf.urls import url, include

from . import views

urlpatterns = [
    url(r'^$', views.ListCreateRides.as_view(), name='ride_list'),
]