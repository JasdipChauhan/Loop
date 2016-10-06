from django.contrib import admin
from .models import Ride, User

# Register your models here.
admin.site.register(User)
admin.site.register(Ride)