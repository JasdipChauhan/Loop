from django.conf.urls import url, include
from rest_framework.urlpatterns import format_suffix_patterns

from . import views

urlpatterns = [
    url(r'^$', views.RideList.as_view(), name='ride_list'),
    url(r'(?P<pk>\d+)/$', views.RideDetail.as_view(), name='ride_detail'),
]

urlpatterns = format_suffix_patterns(urlpatterns)
