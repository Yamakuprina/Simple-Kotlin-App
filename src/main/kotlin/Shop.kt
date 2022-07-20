import java.util.UUID

class Shop(var address: String, var name: String, val id : String = UUID.randomUUID().toString()) {

    var balance = 0.0
    private var products: MutableSet<Product> = mutableSetOf()
    private var productsAccounting: MutableMap<String,Int> = mutableMapOf()

    fun acceptDelivery(product: Product, quantity: Int){
        var prevQuantity = productsAccounting[product.id]
        if (prevQuantity==null){
            products.add(product)
            prevQuantity=0
        }
        productsAccounting[product.id] = quantity+prevQuantity
    }

    fun findProduct(id: String): Product?{
        return products.find { product -> product.id==id }
    }

    fun getProductQuantity(id: String): Int?{
        return productsAccounting[id]
    }

    fun productChangePrice(id: String, newVal: Double): String{
        val product: Product= findProduct(id) ?: return "Product not found"
        product.price=newVal
        return "OK"
    }

    private fun checkSellProduct(customer: Customer, productId: String, quantity: Int): String{
        val productQuantity = productsAccounting[productId] ?: return "Product not found"
        if (productQuantity<quantity) return "Not enough product"
        val product = findProduct(productId) ?: return "Product not found"
        if (quantity*product.price>customer.balance) return "Not enough money"
        return "OK"
    }

    fun sellProduct(customer: Customer, productId: String, quantity: Int): String{
        val check = checkSellProduct(customer, productId, quantity)
        if (check!="OK") return check
        val totalCost = quantity*(findProduct(productId)?.price ?:0.0)
        customer.balance-=totalCost
        balance+=totalCost
        productsAccounting.compute(productId) { k, v -> (v ?: 0)-quantity }
        return "OK"
    }

    fun sellProduct(customer: Customer,productPairs: Map<String,Int>): String{
        var tempBalance = customer.balance
        val tempCustomer:Customer = customer.copy()
        for((key,value) in productPairs){
            tempCustomer.balance=tempBalance
            val check=checkSellProduct(tempCustomer,key,value)
            if (check!="OK") return check
            tempBalance-=value*(findProduct(key)?.price ?: 0.0)
        }
        for ((key,value) in productPairs) sellProduct(customer,key,value)
        return "OK"
    }
}