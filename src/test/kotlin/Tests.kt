import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Tests {
    private val shopManager: ShopManager = ShopManager(mutableSetOf())

    @Test
    fun createShopMakeDeliveryAndBuy(){
        val shop: Shop = shopManager.createShop("Vyazemskiy 5/7", "Diksy")
        val product: Product = Product("Doshirak", 50.0)
        shop.acceptDelivery(product,10)
        val customer = Customer("Amogus",550.0)
        assertEquals("OK",shop.sellProduct(customer, product.id, 10))
    }

    @Test
    fun createShopMakeDeliveryAndBuyListOfProducts(){
        val shop: Shop = shopManager.createShop("Vyazemskiy 5/7", "Diksy")
        val product1: Product = Product("Doshirak", 50.0)
        val product2: Product = Product("Rolton", 40.0)
        val product3: Product = Product("Big-Bon", 60.0)
        shop.acceptDelivery(product1,10)
        shop.acceptDelivery(product2,10)
        shop.acceptDelivery(product3,10)
        val customer = Customer("Amogus",650.0)
        val shoppingList = mapOf<String,Int>(product1.id to 5,product2.id to 3, product3.id to 4)
        assertEquals("OK",shop.sellProduct(customer, shoppingList))
        assertEquals(5, shop.getProductQuantity(product1.id))
        assertEquals(7, shop.getProductQuantity(product2.id))
        assertEquals(6, shop.getProductQuantity(product3.id))
    }

    @Test
    fun notEnoughMoney(){
        val shop: Shop = shopManager.createShop("Vyazemskiy 5/7", "Diksy")
        val product: Product = Product("Doshirak", 50.0)
        shop.acceptDelivery(product,10)
        val customer = Customer("Amogus",499.0)
        assertEquals("Not enough money", shop.sellProduct(customer, product.id, 10))
    }

    @Test
    fun notEnoughProduct(){
        val shop: Shop = shopManager.createShop("Vyazemskiy 5/7", "Diksy")
        val product: Product = Product("Doshirak", 50.0)
        shop.acceptDelivery(product,10)
        val customer = Customer("Amogus",550.0)
        assertEquals("Not enough product", shop.sellProduct(customer, product.id, 11))
    }

    @Test
    fun productNotFound(){
        val shop: Shop = shopManager.createShop("Vyazemskiy 5/7", "Diksy")
        val product: Product = Product("Doshirak", 50.0)
        shop.acceptDelivery(product,10)
        val customer = Customer("Amogus",550.0)
        assertEquals("Product not found", shop.sellProduct(customer, "AnyID", 11))
    }

    @Test
    fun changePriceAndBuy(){
        val shop: Shop = shopManager.createShop("Vyazemskiy 5/7", "Diksy")
        val product: Product = Product("Doshirak", 50.0)
        shop.acceptDelivery(product,10)
        val customer = Customer("Amogus",550.0)
        shop.productChangePrice(product.id, 60.0)
        assertEquals("Not enough money",shop.sellProduct(customer, product.id, 10))
    }

    @Test
    fun findShopWithLowestPrice(){
        val shop1 = shopManager.createShop("Vyazemskiy 5/7", "Diksy")
        val shop2 = shopManager.createShop("Vyazemskiy 40", "Pyaterochka")
        val shop3 = shopManager.createShop("Vyazemskiy 1", "Magnit")
        val product = Product("Doshirak", 50.0)
        shop1.acceptDelivery(product,10)
        shop2.acceptDelivery(product,10)
        shop3.acceptDelivery(product,10)
        shop2.productChangePrice(product.id, 60.0)
        shop3.productChangePrice(product.id, 65.0)
        assertEquals(shop1,shopManager.findShopWithLowestPriceAndNeededQuantity(product.id,10))
    }

    @Test
    fun findShopWithLowestPriceNotEnoughQuantity(){
        val shop1 = shopManager.createShop("Vyazemskiy 5/7", "Diksy")
        val shop2 = shopManager.createShop("Vyazemskiy 40", "Pyaterochka")

        val product = Product("Doshirak", 50.0)
        shop1.acceptDelivery(product,10)
        shop2.acceptDelivery(product,10)
        shop2.productChangePrice(product.id, 60.0)
        assertEquals(null,shopManager.findShopWithLowestPriceAndNeededQuantity(product.id,11))
    }

    @Test
    fun findShopWithLowestPriceProductNotFound(){
        val shop1 = shopManager.createShop("Vyazemskiy 5/7", "Diksy")
        val shop2 = shopManager.createShop("Vyazemskiy 40", "Pyaterochka")
        val product = Product("Doshirak", 50.0)
        shop1.acceptDelivery(product,10)
        shop2.acceptDelivery(product,10)
        shop2.productChangePrice(product.id, 60.0)
        assertEquals(null,shopManager.findShopWithLowestPriceAndNeededQuantity("AnyID",11))
    }
}