from django.conf.urls import url, include

from . import views

urlpatterns = [
    url(r'^$', views.ListCreateRide.as_view(), name='ride_list'),
    url(r'(?P<pk>\d+)/$', views.RetrieveUpdateDeleteRide.as_view(), name='ride_detail'),
    #url(r'^(?P<ride_pk>\d+)/users/$', views.ListCreateUser.as_view(), name='user_list'),
    #url(r'^(?P<ride_pk>\d+)/users/(?P<pk>\d+)/$', views.RetrieveUpdateDeleteUser.as_view(), name='user_detail'),
]