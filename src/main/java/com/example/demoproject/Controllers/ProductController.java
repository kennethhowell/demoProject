package com.example.demoproject.Controllers;

import com.example.demoproject.Models.Product;
import com.example.demoproject.Repos.ProductRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Value("${filestack.api.key}")
    private String apiKey;



    ProductRepo productDao;

    ProductController(ProductRepo productDao){
        this.productDao = productDao;
    }

    @GetMapping("/products")
    public String viewAllProducts(Model model){
        List<Product> currentProducts = productDao.findAll();
        model.addAttribute("products", currentProducts);
        return "product/viewAll";
    }

    @GetMapping("/product/create")
    public String createAProductView(Model model){
            model.addAttribute("product", new Product());
            model.addAttribute("apiKey", apiKey);
        return "product/create";
    }

    @PostMapping("/product/create")
    public String createAProduct(@ModelAttribute Product product

    ){

        Product productInDB = productDao.save(product);

        return "redirect:/product/" + productInDB.getId();
    }


    @GetMapping("/product/{product_id}")
    public String vewAProduct(@PathVariable long product_id, Model model){
        Product productToView = productDao.getOne(product_id);
        model.addAttribute("product",productToView);
        return "product/one";
    }

    @GetMapping("/product/{product_id}/edit")
    public String editAProduct(@PathVariable long product_id, Model model){
        model.addAttribute("product", productDao.getOne(product_id));
        return "product/edit";
    }

    @PostMapping("/product/{product_id}/edit")
    public String EditAProduct(
            @PathVariable long product_id,
            @RequestParam(name = "product_name") String productName,
            @RequestParam(name = "product_description") String productDescription,
            @RequestParam(name = "product_price") float productPrice,
            @RequestParam(name = "product_image_url") String productImgUrl
    ){
        Product productToEdit = new Product(product_id,productName,productDescription,productPrice,productImgUrl);
        productDao.save(productToEdit);

        return "redirect:/product/" + product_id;
    }

    @GetMapping("/product/{product_id}/delete")
    public String deleteAProductForm(@PathVariable long product_id,Model model){
        Product productToDelete = productDao.getOne(product_id);
        model.addAttribute("product", productToDelete);
        return "product/delete";
    }

    @PostMapping("/product/{product_id}/delete")
    public String deleteAProduct(@PathVariable long product_id){
        productDao.deleteById(product_id);

        return "redirect:/products";
    }


    @GetMapping ("/masonry")
    public String masonryView(Model model) {
        List<Product> currentProducts = productDao.findAll();
        model.addAttribute("products", currentProducts);
        return "product/laboveMasonry";}

        @GetMapping("/impossible")
        public String cantbeThisEasy (Model model){
            model.addAttribute("apiKey", apiKey);
            return "product/impossible";
        }
    }