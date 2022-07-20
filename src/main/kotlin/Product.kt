import java.util.UUID

class Product(val name: String, var price: Double = 0.0, val id: String = UUID.randomUUID().toString())