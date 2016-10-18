from django.conf.urls import url, include
from rest_framework.urlpatterns import format_suffix_patterns

from . import views

urlpatterns = [
    url(r'^rides/$', views.RideList.as_view(), name='ride_list'),
    url(r'^rides/(?P<pk>\d+)/$', views.RideDetail.as_view(), name='ride_detail'),

    url(r'^users/$', views.UserList.as_view()),
    url(r'^users/(?P<pk>[0-9]+)/$', views.UserDetail.as_view()),

    url(r'^users/register', views.AuthUser.as_view())
]

urlpatterns = format_suffix_patterns(urlpatterns)
