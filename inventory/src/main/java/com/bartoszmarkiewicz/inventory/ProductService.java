package com.bartoszmarkiewicz.inventory;


import com.bartoszmarkiewicz.inventory.exceptions.ProductNotFoundException;
import com.bartoszmarkiewicz.inventory.exceptions.ProductValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository inventoryRepository;

    // Add products

    public Product addProduct(ProductRecord inventoryProductAddRequest) {

        boolean productExists = inventoryRepository.selectExistsProduct(inventoryProductAddRequest.productName());


        if (productExists) {
            throw new ProductValidationException("Product " + inventoryProductAddRequest.getProductName() + " taken");
        }


        if (inventoryProductAddRequest.getProductQuantity() < 0) {
            throw new ProductValidationException("Product quantity cannot be negative");
        }

        if (inventoryProductAddRequest.getProductPrice() < 0) {
            throw new ProductValidationException("Product price cannot be negative");
        }

        Product inventory = Product.builder()
                .productId(inventoryProductAddRequest.productId())
                .productName(inventoryProductAddRequest.productName())
                .productPrice(inventoryProductAddRequest.productPrice())
                .productQuantity(inventoryProductAddRequest.productQuantity())
                .createdAt(inventoryProductAddRequest.createdAt(LocalDateTime.now()))
                .build();

        inventoryRepository.saveAndFlush(inventory);
        log.info("Product with ID {} added successfully", inventory.getProductId());

        return inventory;
    }

    // Get all products
    public List<Product> getAllProducts() {
        return inventoryRepository.findAll();
    }

    // Get ProductId
    public Optional<Product> getProductById(Integer productId) {
        return inventoryRepository.findById(productId);
    }

    // Update Product
    public Product updateProduct(Product updatedInventory) {
        return inventoryRepository.saveAndFlush(updatedInventory);
    }

    // Remove Product
    public Product removeProduct(Integer productId) {
        return inventoryRepository.findById(productId)
                .map(inventory -> {
                    inventoryRepository.deleteById(productId);
                    log.info("Product with ID {} removed successfully", productId);
                    return inventory;
                }).orElseThrow(() -> new ProductNotFoundException(productId));
    }


}


