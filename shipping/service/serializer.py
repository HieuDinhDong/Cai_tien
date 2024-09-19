from rest_framework import serializers
from .models import User,TypeOrder,Order,Commodities,InfoDriver,Driver,OrderOfUser

class TypeOrderSerializer(serializers.ModelSerializer):
    class Meta:
        model = TypeOrder
        fields = '__all__'
    
class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = '__all__'

class InfoDriverSerializer(serializers.ModelSerializer):
    class Meta:
        model = InfoDriver
        fields = '__all__'

class CommoditiesSerializer(serializers.ModelSerializer):
    class Meta:
        model = Commodities
        fields = '__all__'

class OrderSerializer(serializers.ModelSerializer):
    user = UserSerializer()
    type_order = TypeOrderSerializer()
    commodities = serializers.SerializerMethodField()    
    class Meta:
        model = Order
        fields = '__all__'
    
    def get_commodities(self, obj):
        commodities = Commodities.objects.filter(order=obj)
        return CommoditiesSerializer(commodities, many=True).data
    
class DriverSerializer(serializers.ModelSerializer):
    info_driver = InfoDriverSerializer()
    class Meta:
        model = Driver
        fields = '__all__'
        
class OrderOfUserSerializer(serializers.ModelSerializer):
    driver = DriverSerializer()
    order = OrderSerializer()
    class Meta:
        model = OrderOfUser
        fields = '__all__'
        