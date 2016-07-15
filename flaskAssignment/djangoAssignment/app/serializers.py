from rest_framework import serializers
from models import Product,Category

class ProductSerializer(serializers.ModelSerializer):
    """" category is actually category name
    categoryid is actually the Category object
    Deletions are done in ProductViewSet. The query set does the relevant filtering.

    """
    id = serializers.IntegerField(source='productid', read_only=True)
    code = serializers.CharField(source='productcode', required=True)
    description = serializers.CharField(source='productdescription', required=True)
    price = serializers.IntegerField(source='buyprice', required=True)
    category_id = serializers.IntegerField(source='categoryid.id', read_only=True)
    category = serializers.CharField(max_length=100, required=True, write_only=True)

    class Meta:
        model = Product
        fields = ('id', 'code', 'description', 'price', 'category_id', 'category')

    def create(self, validated_data):
        validated_data['categoryid'], created = Category.objects.get_or_create(name=validated_data.get('category'))
        del validated_data['category']
        return Product.objects.create(**validated_data)

    def update(self, instance, validated_data):
        validated_data['categoryid'], created = Category.objects.get_or_create(name=validated_data.get('category'))
        instance.productcode = validated_data.get('code', instance.productcode)
        instance.productdescription = validated_data.get('description', instance.productdescription)
        instance.buyprice = validated_data.get('price', instance.buyprice)
        instance.categoryid = validated_data.get('categoryid', instance.categoryid)
        instance.save()
        return instance
