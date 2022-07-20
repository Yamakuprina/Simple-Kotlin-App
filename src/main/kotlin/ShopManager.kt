import java.util.UUID

class ShopManager(var shops: MutableSet<Shop>) {
    fun createShop(address: String, name: String, id : String = UUID.randomUUID().toString()): Shop{
        val shop = Shop(id, address, name)
        shops.add(shop)
        return shop
    }

    fun findShopWithLowestPriceAndNeededQuantity(productId: String, quantity: Int):Shop? {
        var min: Double = Double.MAX_VALUE
        var minShop: Shop? = null
        for (shop in shops){
            val price = shop.findProduct(productId)?.price ?: continue
            if (price >= min) continue
            if ((shop.getProductQuantity(productId) ?: continue) < quantity) continue
            min = price
            minShop = shop
        }
        return minShop
    }
}