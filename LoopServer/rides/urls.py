from django.conf.urls import url, include
from rest_framework.urlpatterns import format_suffix_patterns

from . import views

urlpatterns = [
    url(r'^$', views.ride_list, name='ride_list'),
    url(r'(?P<pk>\d+)/$', views.ride_detail, name='ride_detail'),
    #url(r'^(?P<ride_pk>\d+)/users/$', views.ListCreateUser.as_view(), name='user_list'),
    #url(r'^(?P<ride_pk>\d+)/users/(?P<pk>\d+)/$', views.RetrieveUpdateDeleteUser.as_view(), name='user_detail'),
]

urlpatterns = format_suffix_patterns(urlpatterns)
