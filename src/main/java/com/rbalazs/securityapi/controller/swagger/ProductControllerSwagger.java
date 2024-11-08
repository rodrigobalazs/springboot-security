package com.rbalazs.securityapi.controller.swagger;

import com.rbalazs.securityapi.controller.ProductController;
import com.rbalazs.securityapi.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import java.util.List;

/**
 * Swagger interface related to {@link ProductController}.
 * API Documentation/Swagger at => http://<project_url>/swagger-ui/index.html
 */
@Tag(name = "Product API", description = "API endpoints related to Products")
public interface ProductControllerSwagger {

    @Operation(summary = "Retrieves all Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns a JSON Array with all the Products",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Product.class)))})})
    public ResponseEntity<List<Product>> getProducts();

    @Operation(summary = "Saves a new Product")
    public Product saveProduct(@RequestBody Product product);
}
