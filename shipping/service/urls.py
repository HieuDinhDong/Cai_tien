from django.urls import path
from service import views

urlpatterns = [
    path("register_user",views.register_user,name="register_user"),
    path('user_detail/<int:id_user>',views.user_detail,name="user_detail"),
    path('order_detail/<int:id_order>',views.order_detail,name="order_detail"),
    path('update_user/<int:id_user>',views.update_user,name="update_user"),
    path("login_user",views.login_user,name="login_user"),
    path("register_driver",views.register_driver,name="register_driver"),
    path('driver_detail/<int:id_driver>',views.driver_detail,name="driver_detail"),
    path('refuse_order/<int:id_driver>/<int:id_order>',views.refuse_order,name="refuse_order"),
    path('update_driver/<int:id_driver>',views.update_driver,name="update_driver"),
    path("login_driver",views.login_driver,name="login_driver"),
    path("add_order/<int:id_user>/<int:id_type>",views.add_order,name="add_order"),
    path("delete_order/<int:id_order>",views.delete_order,name="delete_order"),
    path("get_list_order_of_user/<int:id_user>",views.get_list_order_of_user,name="get_list_order_of_user"),
    path("get_list_type_order",views.get_list_type_order,name="get_list_type_order"),
    path("static_by_day/<int:id_driver>",views.static_by_day,name="static_by_day"),
    path("static_by_month/<int:id_driver>",views.static_by_month,name="static_by_month"),
    path("get_list_order_of_driver/<int:id_driver>",views.get_list_order_of_driver,name="get_list_order_of_driver"),
    path("receive_order_by_driver/<int:id_driver>/<int:id_order>",views.receive_order_by_driver,name="receive_order_by_driver"),
    path("success_order_by_driver/<int:id_driver>/<int:id_order>",views.success_order_by_driver,name="success_order_by_driver"),
    path("add_commodities/<int:id_order>",views.add_commodities,name="add_commodities"),
    path('change_password_user/<int:id_user>',views.change_password_user,name="change_password_user"),
    path('change_password_driver/<int:id_driver>',views.change_password_driver,name="change_password_driver"),
    path('delete_order_success_driver/<int:id_order>',views.delete_order_success_driver,name="delete_order_success_driver"),
]