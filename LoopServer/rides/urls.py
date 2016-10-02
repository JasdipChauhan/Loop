from django.conf.urls import url, include

from . import views

urlpatterns = [
    url(r'^$', views.ListCreateRides.as_view(), name='ride_list'),
    url(r'(?P<pk>\d+)/$', views.RetrieveUpdateDeleteRide.as_view(), name='ride_detail')
]