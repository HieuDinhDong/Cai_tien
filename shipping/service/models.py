from typing import Any
from django.db import models
import json
# Create your models here.

class TypeOrder(models.Model):
    id_type=models.AutoField(primary_key=True)
    name_type=models.CharField(max_length=250)
    describe=models.CharField(max_length=250)
    
    def __str__(self):
        return self.id_part
    
    def set_id_type(self, id_type):
        self.id_type = id_type
    
    def set_name_type(self,name_type):
        self.name_type=name_type
        
    def set_describe(self,describe):
        self.describe=describe

class User(models.Model):
    id_user=models.AutoField(primary_key=True)
    full_name=models.CharField(max_length=250)
    email=models.EmailField()
    phone=models.CharField(max_length=250)
    password=models.CharField(max_length=250)
    
    def __str__(self):
        return self.id_user
    
    def set_id_user(self, id_user):
        self.id_user = id_user
    
    def set_full_name(self, full_name):
        self.full_name = full_name
    
    def set_email(self, email):
        self.email = email
    
    def set_phone(self, phone):
        self.phone = phone
        
    def set_password(self, password):
        self.password = password
    



class InfoDriver(models.Model):
    id_info=models.AutoField(primary_key=True)
    front_cccd=models.CharField(max_length=250)
    behind_cccd=models.CharField(max_length=250)
    front_license=models.CharField(max_length=250)
    behind_license=models.CharField(max_length=250)
    front_regis=models.CharField(max_length=250)
    behind_regis=models.CharField(max_length=250)

    def set_id_info(self, id_info):
        self.id_info = id_info
    
    def set_front_cccd(self, front_cccd):
        self.front_cccd = front_cccd
        
    def set_behind_cccd(self, behind_cccd):
        self.behind_cccd = behind_cccd
        
    def set_front_license(self, front_license):
        self.front_license = front_license
        
    def set_behind_license(self, behind_license):
        self.behind_license = behind_license
    
    def set_front_regis(self, front_regis):
        self.front_regis = front_regis
        
    def set_behind_regis(self, behind_regis):
        self.behind_regis = behind_regis

class Driver(models.Model):
    id_driver=models.AutoField(primary_key=True)
    full_name=models.CharField(max_length=250)
    email=models.EmailField()
    phone=models.CharField(max_length=250)
    password=models.CharField(max_length=250)
    longitude=models.FloatField()
    latitude=models.FloatField()
    info_driver  =  models.ForeignKey(InfoDriver, null=True, on_delete=models.CASCADE)
    
    def __str__(self):
        return self.id_driver
    
    def set_id_driver(self, id_driver):
        self.id_driver = id_driver
    
    def set_full_name(self, full_name):
        self.full_name = full_name
    
    def set_email(self, email):
        self.email = email
    
    def set_phone(self, phone):
        self.phone = phone
    
    def set_longitude(self, longitude):
        self.longitude = longitude
        
    def set_latitude(self, latitude):
        self.latitude = latitude
        
    def set_password(self, password):
        self.password = password
        
    def set_info_driver(self, info_driver):
        self.info_driver = info_driver

class Order(models.Model):
    id_order=models.AutoField(primary_key=True)
    address_start=models.CharField(max_length=250)
    address_end=models.CharField(max_length=250)
    phone_receiver=models.CharField(max_length=250)
    name_receiver=models.CharField(max_length=250)
    price_ship=models.FloatField()
    note=models.CharField(max_length=250)
    time=models.CharField(max_length=250)
    status=models.IntegerField()
    longitude=models.FloatField()
    latitude=models.FloatField()
    user  =  models.ForeignKey(User, null=True, on_delete=models.CASCADE)
    type_order  =  models.ForeignKey(TypeOrder, null=True, on_delete=models.CASCADE)
    
    def __str__(self):
        return self.id_order
    
    def set_id_order(self, id_order):
        self.id_order = id_order
    
    def set_address_start(self, address_start):
        self.address_start = address_start
        
    def set_longitude(self, longitude):
        self.longitude = longitude
        
    def set_latitude(self, latitude):
        self.latitude = latitude
    
    def set_time(self, time):
        self.time = time
    
    def set_address_end(self, address_end):
        self.address_end = address_end
    
    def set_phone_receiver(self, phone_receiver):
        self.phone_receiver = phone_receiver
        
    def set_name_receiver(self, name_receiver):
        self.name_receiver = name_receiver
        
    def set_price_ship(self, price_ship):
        self.price_ship = price_ship
        
    def set_note(self, note):
        self.note = note
    
    def set_status(self, status):
        self.status = status
        
    def set_user(self, user):
        self.user = user
        
    def set_type_order(self, type_order):
        self.type_order = type_order


class Commodities(models.Model):
    id_com=models.AutoField(primary_key=True)
    name_com=models.CharField(max_length=250)
    describe_com=models.TextField()
    weight=models.FloatField()
    price=models.FloatField()
    order  =  models.ForeignKey(Order, null=True, on_delete=models.CASCADE)
    
    def __str__(self):
        return self.id_com
    
    def set_id_com(self, id_com):
        self.id_com = id_com
    
    def set_name_com(self,name_com):
        self.name_com=name_com
        
    def set_describe_com(self,describe_com):
        self.describe_com=describe_com
        
    def set_weight(self,weight):
        self.weight=weight
        
    def set_price(self,price):
        self.price=price
        
           
    def set_order(self,order):
        self.order=order

class OrderOfUser(models.Model):
    id=models.AutoField(primary_key=True)
    driver  =  models.ForeignKey(Driver, null=True, on_delete=models.CASCADE)
    order  =  models.ForeignKey(Order, null=True, on_delete=models.CASCADE)


class DriverRefuse(models.Model):
    id=models.AutoField(primary_key=True)
    driver  =  models.ForeignKey(Driver, null=True, on_delete=models.CASCADE)
    order  =  models.ForeignKey(Order, null=True, on_delete=models.CASCADE)
