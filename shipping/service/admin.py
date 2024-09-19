from django.contrib import admin
from .models import User,TypeOrder,Order,Commodities,InfoDriver,Driver,OrderOfUser,DriverRefuse

# Register your models here.
admin.site.register(User)
admin.site.register(TypeOrder)
admin.site.register(Order)
admin.site.register(Commodities)
admin.site.register(InfoDriver)
admin.site.register(Driver)
admin.site.register(OrderOfUser)
admin.site.register(DriverRefuse)