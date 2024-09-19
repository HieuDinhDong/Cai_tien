from django.shortcuts import render
from rest_framework.decorators import api_view
from rest_framework.response import Response
from .models import User,TypeOrder,Order,Commodities,InfoDriver,Driver,OrderOfUser,DriverRefuse
from .serializer import UserSerializer,TypeOrderSerializer,OrderOfUserSerializer,CommoditiesSerializer,OrderSerializer,InfoDriverSerializer,DriverSerializer
from rest_framework import status
from geopy.distance import geodesic
# Create your views here.
@api_view(['POST'])
def register_user(request):
    data=request.GET
    user=User(full_name=data.get('full_name'),
            email=data.get('email'),phone=data.get('phone'),password=data.get('password'))
    user.save()
    return Response({"data":"","message":"Success","code":200},status=status.HTTP_200_OK)

def tinh_khoang_cach(coor1, coor2):
    khoang_cach = geodesic(coor1, coor2).meters
    return khoang_cach

@api_view(['POST'])
def register_driver(request):
    data=request.GET
    driver=Driver(full_name=data.get('full_name'),
                email=data.get('email'),phone=data.get('phone'),password=data.get('password'),longitude=data.get('longitude'),
                               latitude=data.get('latitude'),)
    driver.save()
    return Response({"data":"","message":"Success","code":200},status=status.HTTP_200_OK)
        

@api_view(['GET'])
def login_user(request):
    data=request.GET
    list_user=User.objects.all()
    u=User()
    check=False
    for user in list_user:
        if user.password==data.get('password') and user.phone==data.get('phone'):
            check=True
            u=user
            break
    if check==True:
        serializer = UserSerializer(u)
        return Response({"data":serializer.data,"message":"Success","code":200},status=status.HTTP_200_OK)
    else:
        serializer = UserSerializer(u)
        return Response({"data":serializer.data,"message":"Wrong","code":400},status=status.HTTP_200_OK)
    
@api_view(['GET'])
def login_driver(request):
    data=request.GET
    list_driver=Driver.objects.all()
    u=Driver()
    check=False
    for driver in list_driver:
        if driver.password==data.get('password') and driver.phone==data.get('phone'):
            check=True
            u=driver
            break
    if check==True:
        serializer = DriverSerializer(u)
        return Response({"data":serializer.data,"message":"Success","code":200},status=status.HTTP_200_OK)
    else:
        serializer = UserSerializer(u)
        return Response({"data":serializer.data,"message":"Wrong","code":400},status=status.HTTP_200_OK)

@api_view(['GET'])
def user_detail(request,id_user):
    data=request.GET
    user=User.objects.get(id_user=id_user)
    serializer = UserSerializer(user)
    return Response({"data":serializer.data,"message":"Success","code":200},status=status.HTTP_200_OK)


@api_view(['GET'])
def order_detail(request,id_order):
    data=request.GET
    order=Order.objects.get(id_order=id_order)
    serializer = OrderSerializer(order)
    return Response({"data":serializer.data,"message":"Success","code":200},status=status.HTTP_200_OK)
 
@api_view(['GET'])
def driver_detail(request,id_driver):
    data=request.GET
    driver=Driver.objects.get(id_driver=id_driver)
    serializer = DriverSerializer(driver)
    return Response({"data":serializer.data,"message":"Success","code":200},status=status.HTTP_200_OK)
 
@api_view(['PUT'])
def update_user(request,id_user):
    data=request.GET
    user=User.objects.get(id_user=id_user)
    user.set_full_name(data.get('full_name'))
    user.set_email(data.get('email'))
    user.set_phone(data.get('phone'))
    user.save()
    serializer = UserSerializer(user)
    return Response({"data":serializer.data,"message":"Success","code":200},status=status.HTTP_200_OK)

@api_view(['PUT'])
def update_driver(request,id_driver):
    data=request.GET
    driver=Driver.objects.get(id_driver=id_driver)
    driver.set_full_name(data.get('full_name'))
    driver.set_email(data.get('email'))
    driver.set_phone(data.get('phone'))
    driver.save()
    serializer = DriverSerializer(driver)
    return Response({"data":serializer.data,"message":"Success","code":200},status=status.HTTP_200_OK)
    

@api_view(['POST'])
def add_order(request,id_user,id_type):
    data=request.GET
    user=User.objects.get(id_user=id_user)
    type_order=TypeOrder.objects.get(id_type=id_type)
    order=Order(address_start=data.get('address_start'),
                               address_end=data.get('address_end'),
                               phone_receiver=data.get('phone_receiver'),
                               name_receiver=data.get('name_receiver'),
                               price_ship=data.get('price_ship'),
                               time=data.get('time'),
                               note=data.get('note'),
                               status=data.get('status'),
                               longitude=data.get('longitude'),
                               latitude=data.get('latitude'),
                               user=user,
                               type_order=type_order)
    order.save()
    return Response({"data":str(order.id_order),"message":"Success","code":201},status=status.HTTP_200_OK)

@api_view(['POST'])
def refuse_order(request,id_driver,id_order):
    driver=Driver.objects.get(id_driver=id_driver)
    order=Order.objects.get(id_order=id_order)
    orderOfu= DriverRefuse(driver=driver,order=order)
    orderOfu.save()
    print(orderOfu.id)
    return Response({"data":"","message":"Success","code":200},status=status.HTTP_200_OK)

@api_view(['POST'])
def add_commodities(request,id_order):
    data=request.GET
    order=Order.objects.get(id_order=id_order)
    Commodities.objects.create(name_com=data.get('name_com'),
                               describe_com=data.get('describe_com'),
                               weight=data.get('weight'),
                               price=data.get('price'),
                               order  =order)
    return Response({"data":"","message":"Success","code":201},status=status.HTTP_200_OK)

@api_view(['DELETE'])
def delete_order(request,id_order):
    data=request.GET
    order=Order.objects.get(id_order=id_order)
    list_com=Commodities.objects.filter(order=order)
    for com in list_com:
        com.delete()
    order.delete()
    return Response({"data":"","message":"Success","code":200},status=status.HTTP_200_OK)

@api_view(['GET'])
def get_list_order_of_user(request,id_user):
    data=request.GET
    sta=data.get('status')
    user=User.objects.get(id_user=id_user)
    list_order=Order.objects.filter(user=user,status=sta)
    serializer=OrderSerializer(list_order,many=True)
    return Response({"data":serializer.data,"message":"Success","code":200},status=status.HTTP_200_OK)


@api_view(['GET'])
def get_list_order_of_driver(request,id_driver):
    data=request.GET
    sta=int(data.get('status'))
    longitude=data.get('longitude')
    latitude=data.get('latitude')
    driver=Driver.objects.get(id_driver=id_driver)
    driver.set_longitude(longitude)
    driver.set_latitude(latitude)
    driver.save()
    list_order=[]
    if sta==0:
        list_order_new=[]
        list_order=Order.objects.filter(status=0)
        for order in list_order:
            list_driver=DriverRefuse.objects.filter(order=order)
            check=True
            for d in list_driver:
                if d.driver.id_driver==driver.id_driver:
                    check=False
                    break
            list_d=Driver.objects.all()
            khoangc=tinh_khoang_cach((latitude,longitude),(order.latitude,order.longitude))
            
            for d in list_d:
                checkRefuse=False
                for d1 in list_driver:
                    if d.id_driver==d1.driver.id_driver:
                        checkRefuse=True
                        break
                if checkRefuse==True:
                    continue
                if tinh_khoang_cach((d.latitude,d.longitude),(order.latitude,order.longitude))<khoangc:
                    check=False
                    break
            if check==True:
                list_order_new.append(order)
        list_order=list_order_new
                    
    else:
        driver=Driver.objects.get(id_driver=id_driver)
        list_order_of_driver=OrderOfUser.objects.filter(driver=driver)

        for order_of in list_order_of_driver:
            if order_of.order.status==sta:
                list_order.append(order_of.order)
    serializer=OrderSerializer(list_order,many=True)
    return Response({"data":serializer.data,"message":"Success","code":200},status=status.HTTP_200_OK)

@api_view(['GET'])
def static_by_day(request,id_driver):
    data=request.GET
    day=data.get('day')
    driver=Driver.objects.get(id_driver=id_driver)
    list_order=OrderOfUser.objects.filter(driver=driver)
    list_order_new=[]
    for order in list_order:
        if order.order.time==day and order.order.status==2:
            list_order_new.append(order.order)
    serializer=OrderSerializer(list_order_new,many=True)
    return Response({"data":serializer.data,"message":"Success","code":200},status=status.HTTP_200_OK)

@api_view(['GET'])
def static_by_month(request,id_driver):
    data=request.GET
    day=data.get('day')
    driver=Driver.objects.get(id_driver=id_driver)
    list_order=OrderOfUser.objects.filter(driver=driver)
    list_order_new=[]
    for order in list_order:
        if order.order.time[3:]==day[3:] and order.order.status==2:
            list_order_new.append(order.order)
    serializer=OrderSerializer(list_order_new,many=True)
    return Response({"data":serializer.data,"message":"Success","code":200},status=status.HTTP_200_OK)

@api_view(['GET'])
def get_list_type_order(request):
    data=request.GET
    list_type_order=TypeOrder.objects.all()
    serializer=TypeOrderSerializer(list_type_order,many=True)
    return Response({"data":serializer.data,"message":"Success","code":200},status=status.HTTP_200_OK)

@api_view(['PUT'])
def receive_order_by_driver(request,id_driver,id_order):
    data=request.GET
    driver=Driver.objects.get(id_driver=id_driver)
    order=Order.objects.get(id_order=id_order)
    order.set_status(1)
    order.save()
    OrderOfUser.objects.create(driver=driver,order=order)
    return Response({"data":"","message":"Success","code":200},status=status.HTTP_200_OK)

@api_view(['PUT'])
def success_order_by_driver(request,id_driver,id_order):
    data=request.GET
    driver=Driver.objects.get(id_driver=id_driver)
    order=Order.objects.get(id_order=id_order)
    order.set_status(2)
    order.save()
    return Response({"data":"","message":"Success","code":200},status=status.HTTP_200_OK)

@api_view(['PUT'])
def change_password_user(request,id_user):
    data=request.GET
    user=User.objects.get(id_user=id_user)
    user.set_password(data.get('password'))
    user.save()
    return Response({"data":"","message":"Success","code":200},status=status.HTTP_200_OK)

@api_view(['PUT'])
def change_password_driver(request,id_driver):
    data=request.GET
    driver=Driver.objects.get(id_driver=id_driver)
    driver.set_password(data.get('password'))
    driver.save()
    return Response({"data":"","message":"Success","code":200},status=status.HTTP_200_OK)


@api_view(['DELETE'])
def delete_order_success_driver(request,id_order):
    data=request.GET
    order=Order.objects.get(id_order=id_order)
    order_of_driver=OrderOfUser.objects.get(order=order)
    order_of_driver.delete()
    return Response({"data":"","message":"Success","code":200},status=status.HTTP_200_OK)