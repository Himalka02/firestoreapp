package com.example.firebaseapp2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firestore = FirebaseFirestore.getInstance()

        fun addProduct(productId: String, name: String, price: Double) {
            val product = hashMapOf(
                "name" to name,
                "price" to price
            )
            firestore.collection("products").document(productId).set(product)
                .addOnSuccessListener {
                    Toast.makeText(this, "Product added successfully!",
                        Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show()
                }
        }

        fun getProducts() {
            firestore.collection("products").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val name = document.getString("name")
                        val price = document.getDouble("price")
                        Toast.makeText(this, "Product: $name, Price: $price",
                            Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to retrieve products", Toast.LENGTH_SHORT).show()
                }
        }

        fun updateProduct(productId: String, newName: String, newPrice: Double) {
            val updates = hashMapOf<String, Any>(
                "name" to newName,
                "price" to newPrice
            )
            firestore.collection("products").document(productId).update(updates)
                .addOnSuccessListener {
                    Toast.makeText(this, "Product updated successfully!",
                        Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update product", Toast.LENGTH_SHORT).show()
                }
        }

        fun deleteProduct(productId: String) {
            firestore.collection("products").document(productId).delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Product deleted successfully!",
                        Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to delete product", Toast.LENGTH_SHORT).show()
                }
        }


        val productIdEditText = findViewById<EditText>(R.id.productIdEditText)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val priceEditText = findViewById<EditText>(R.id.priceEditText)
        val addProductButton = findViewById<Button>(R.id.addProductButton)
        val getProductsButton = findViewById<Button>(R.id.getProductsButton)
        val updateProductButton = findViewById<Button>(R.id.updateProductButton)
        val deleteProductButton = findViewById<Button>(R.id.deleteProductButton)
        addProductButton.setOnClickListener {
            val productId = productIdEditText.text.toString()
            val name = nameEditText.text.toString()
            val price = priceEditText.text.toString().toDoubleOrNull()
            if (productId.isNotEmpty() && name.isNotEmpty() && price != null) {
                addProduct(productId, name, price)
            } else {
                Toast.makeText(this, "Please enter valid product details",
                    Toast.LENGTH_SHORT).show()
            }
        }
        getProductsButton.setOnClickListener {
            getProducts()
        }
        updateProductButton.setOnClickListener {
            val productId = productIdEditText.text.toString()
            val name = nameEditText.text.toString()
            val price = priceEditText.text.toString().toDoubleOrNull()
            if (productId.isNotEmpty() && name.isNotEmpty() && price != null) {
                updateProduct(productId, name, price)
            } else {
                Toast.makeText(this, "Please enter valid product details",
                    Toast.LENGTH_SHORT).show()
            }
        }
        deleteProductButton.setOnClickListener {
            val productId = productIdEditText.text.toString()
            if (productId.isNotEmpty()) {
                deleteProduct(productId)
            } else {
                Toast.makeText(this, "Please enter a valid product ID",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}
