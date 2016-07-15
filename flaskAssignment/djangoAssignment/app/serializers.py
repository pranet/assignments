# from rest_framework import serializers
# from models import Product
#
#
# class ProductSerializer(serializers.ModelSerializer):
#     id = serializers.IntegerField(source='productid')
#     code = serializers.CharField(source='productcode')
#     description = serializers.CharField(source='productdescription')
#     price = serializers.IntegerField(source='buyprice')
#     category_id = serializers.CharField(source='categoryid.id')
#     category = serializers.CharField(source='categoryid.name')
#
#     class Meta:
#         model = Product
#         fields = ('id', 'code', 'description', 'price', 'category_id', 'category')