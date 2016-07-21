from rest_framework import serializers
from models import Product
from models import Category
from models import Users
from models import Orders
from models import Orderdetails

class ProductSerializer(serializers.ModelSerializer):
    """" category is actually category name
    categoryid is actually the Category object
    Deletions are done in ProductViewSet. The query set does the relevant filtering.

    """
    id = serializers.IntegerField(source='productid', read_only=True)
    code = serializers.CharField(source='productcode')
    description = serializers.CharField(source='productdescription')
    name = serializers.CharField(source='productname', required= False)

    price = serializers.IntegerField(source='buyprice')
    category_id = serializers.IntegerField(source='categoryid.id', read_only=True)

    category = serializers.CharField(max_length=100, required=False)

    class Meta:
        model = Product
        fields = ('id', 'code', 'description', 'price', 'category_id', 'category', 'name')

    def create(self, validated_data):
        validated_data['categoryid'], created = Category.objects.get_or_create(name=validated_data.get('category'))
        del validated_data['category']
        return Product.objects.create(**validated_data)

    def update(self, instance, validated_data):
        """self.partial is true if update is called via a patch request.
        It is false for a put request"""
        if 'category' in validated_data:
            validated_data['categoryid'], created = Category.objects.get_or_create(name=validated_data.get('category'))
        print validated_data
        instance.productcode = validated_data.get('productcode', instance.productcode if self.partial else None)
        instance.productdescription = validated_data.get('productdescription', instance.productdescription if self.partial else None)
        instance.buyprice = validated_data.get('buyprice', instance.buyprice if self.partial else None)
        instance.categoryid = validated_data.get('categoryid', instance.categoryid if self.partial else None)
        instance.productname = validated_data.get('productname', instance.productname if self.partial else None)

        instance.save()
        return instance


class OrdersSerializer(serializers.ModelSerializer):
    id = serializers.IntegerField(source='orderid', read_only=True)
    username = serializers.CharField(source='userid.customername', required=False)
    address = serializers.CharField(source='userid.address', required=False)
    # userid = serializers.CharField(source='userid', required=False)
    class Meta:
        model = Orders
        fields = ('id', 'username', 'address', 'status')

    def update(self, instance, validated_data):
        """userid object is created if and only if atleast one of username/address is specified
            If username is given,
                Fetch the user object using get_or_create(), and update it's address
                according to PUT/PATCH criteria. Make the instance point to this.
            If username is not given,
                If request is PUT and
                    address is given : INVALID CASE. ignored.
                    address is not given: Instance shall now point to none
                If request is PATCH
                    fetch the current instance, and update the address if it is in the PATCH.
        """
        user_id = validated_data.get('userid', instance.userid if self.partial else None)
        username = user_id.get('customername', None)
        address = user_id.get('address', None)

        user = None
        # if username is given
        if username is not None:
            user, created = Users.objects.get_or_create(customername=username)
            user.address = address if address is not None else user.address if self.partial else None
            user.save()
        else:
            if self.partial:
                user = instance.userid
                user.address = address if address is not None else user.address
                user.save()
            # for put, address must be none: so user would be just none
        instance.status = validated_data.get('status', instance.status if self.partial else None)
        instance.userid = user
        instance.save()
        return instance

    def create(self, validated_data):
        """ If both user and address are given, address is overwritten
            If address is given but user is not, bad request. Should handle later.
            If user is given, but addresss is not, ignore address
            If neither is given, cool
            userid object is created if and only if atleast one of username/address is specified
        """
        user_id = validated_data.get('userid', dict())
        username = user_id.get('customername', None)
        address = user_id.get('address', None)
        user = None
        if username is not None:
            user, created = Users.objects.get_or_create(customername=username)
        if user is not None and address is not None:
            user.address = address
        if user is not None:
            user.save()
        validated_data['userid'] = user
        return Orders.objects.create(**validated_data)


class OrderDetailsSerializer(serializers.ModelSerializer):
    id = serializers.IntegerField(source='orderdetailsid', read_only=True)
    order_id = serializers.IntegerField(source='orderid.orderid', required=False)
    product_id = serializers.IntegerField(source='productid.productid', required=True)
    price = serializers.IntegerField(source='sellprice', required=True)

    class Meta:
        model = Orderdetails
        fields = ('id', 'order_id', 'product_id', 'price', 'quantity')