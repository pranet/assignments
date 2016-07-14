# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from __future__ import unicode_literals

from django.db import models


class Address(models.Model):
    addressid = models.AutoField(db_column='addressID', primary_key=True)  # Field name made lowercase.
    userid = models.ForeignKey('Users', models.DO_NOTHING, db_column='userID')  # Field name made lowercase.
    addressline1 = models.CharField(db_column='addressLine1', max_length=100, blank=True, null=True)  # Field name made lowercase.
    addressline2 = models.CharField(db_column='addressLine2', max_length=100, blank=True, null=True)  # Field name made lowercase.
    city = models.CharField(max_length=100, blank=True, null=True)
    state = models.CharField(max_length=100, blank=True, null=True)
    country = models.CharField(max_length=100, blank=True, null=True)
    postalcode = models.CharField(db_column='postalCode', max_length=100, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Address'


class Category(models.Model):
    name = models.CharField(max_length=100, blank=True, null=True)
    description = models.TextField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'Category'

    def __unicode__(self):
        return self.name if self.name != None else ("ID :" + str(self.ID))


class Feedback(models.Model):
    feedbackid = models.AutoField(db_column='feedbackID', primary_key=True)  # Field name made lowercase.
    userid = models.ForeignKey('Users', models.DO_NOTHING, db_column='userID')  # Field name made lowercase.
    date = models.DateTimeField(blank=True, null=True)
    statement = models.CharField(max_length=200, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'Feedback'


class Orderdetails(models.Model):
    orderdetailsid = models.AutoField(db_column='orderDetailsID', primary_key=True)  # Field name made lowercase.
    orderid = models.ForeignKey('Orders', models.DO_NOTHING, db_column='orderID')  # Field name made lowercase.
    productid = models.ForeignKey('Product', models.DO_NOTHING, db_column='productID')  # Field name made lowercase.
    quantity = models.IntegerField(blank=True, null=True)
    sellprice = models.IntegerField(db_column='sellPrice', blank=True, null=True)  # Field name made lowercase.
    buyprice = models.IntegerField(db_column='buyPrice', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'OrderDetails'
    def __unicode__(self):
        return str(self.orderid.orderdate);


class Orders(models.Model):
    orderid = models.AutoField(db_column='orderID', primary_key=True)  # Field name made lowercase.
    userid = models.ForeignKey('Users', models.DO_NOTHING, db_column='userID', blank=True, null=True)  # Field name made lowercase.
    orderdate = models.DateField(db_column='orderDate', blank=True, null=True)  # Field name made lowercase.
    status = models.CharField(max_length=100, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'Orders'


class Product(models.Model):
    productid = models.AutoField(db_column='productID', primary_key=True)  # Field name made lowercase.
    productname = models.CharField(db_column='productName', max_length=100, blank=True, null=True)  # Field name made lowercase.
    productcode = models.CharField(db_column='productCode', max_length=100, blank=True, null=True)  # Field name made lowercase.
    quantityinstock = models.IntegerField(db_column='quantityInStock', blank=True, null=True)  # Field name made lowercase.
    buyprice = models.IntegerField(db_column='buyPrice', blank=True, null=True)  # Field name made lowercase.
    sellprice = models.IntegerField(db_column='sellPrice', blank=True, null=True)  # Field name made lowercase.
    productdescription = models.CharField(db_column='productDescription', max_length=1000, blank=True, null=True)  # Field name made lowercase.
    isavailable = models.IntegerField(db_column='isAvailable', blank=True, null=True)  # Field name made lowercase.
    categoryid = models.ForeignKey(Category, models.DO_NOTHING, db_column='categoryID', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Product'

    def __unicode__(self):
        return self.productname if self.productname != None else ("ID : " + str(self.productid))


class Users(models.Model):
    userid = models.AutoField(db_column='userID', primary_key=True)  # Field name made lowercase.
    customername = models.CharField(db_column='customerName', max_length=100, blank=True, null=True)  # Field name made lowercase.
    contactfirstname = models.CharField(db_column='contactFirstName', max_length=100, blank=True, null=True)  # Field name made lowercase.
    contactlastname = models.CharField(db_column='contactLastName', max_length=100, blank=True, null=True)  # Field name made lowercase.
    phone = models.CharField(max_length=100, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'Users'


class AuditLog(models.Model):
    timestamp = models.DateTimeField(blank=True, null=True)
    url = models.CharField(max_length=100, blank=True, null=True)
    parameters = models.CharField(max_length=1000, blank=True, null=True)
    responsecode = models.IntegerField(db_column='responseCode', blank=True, null=True)  # Field name made lowercase.
    ipaddress = models.CharField(db_column='ipAddress', max_length=100, blank=True, null=True)  # Field name made lowercase.
    requesttype = models.CharField(db_column='requestType', max_length=100, blank=True, null=True)  # Field name made lowercase.
    requesttime = models.IntegerField(db_column='requestTime', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'audit_log'


class AuthGroup(models.Model):
    name = models.CharField(unique=True, max_length=80)

    class Meta:
        managed = False
        db_table = 'auth_group'


class AuthGroupPermissions(models.Model):
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)
    permission = models.ForeignKey('AuthPermission', models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_group_permissions'
        unique_together = (('group', 'permission'),)


class AuthPermission(models.Model):
    name = models.CharField(max_length=255)
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING)
    codename = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'auth_permission'
        unique_together = (('content_type', 'codename'),)


class AuthUser(models.Model):
    password = models.CharField(max_length=128)
    last_login = models.DateTimeField(blank=True, null=True)
    is_superuser = models.IntegerField()
    username = models.CharField(unique=True, max_length=30)
    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=30)
    email = models.CharField(max_length=254)
    is_staff = models.IntegerField()
    is_active = models.IntegerField()
    date_joined = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'auth_user'


class AuthUserGroups(models.Model):
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_groups'
        unique_together = (('user', 'group'),)


class AuthUserUserPermissions(models.Model):
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    permission = models.ForeignKey(AuthPermission, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_user_permissions'
        unique_together = (('user', 'permission'),)


class DjangoAdminLog(models.Model):
    action_time = models.DateTimeField()
    object_id = models.TextField(blank=True, null=True)
    object_repr = models.CharField(max_length=200)
    action_flag = models.SmallIntegerField()
    change_message = models.TextField()
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING, blank=True, null=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'django_admin_log'


class DjangoContentType(models.Model):
    app_label = models.CharField(max_length=100)
    model = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'django_content_type'
        unique_together = (('app_label', 'model'),)


class DjangoMigrations(models.Model):
    app = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    applied = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_migrations'


class DjangoSession(models.Model):
    session_key = models.CharField(primary_key=True, max_length=40)
    session_data = models.TextField()
    expire_date = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_session'
