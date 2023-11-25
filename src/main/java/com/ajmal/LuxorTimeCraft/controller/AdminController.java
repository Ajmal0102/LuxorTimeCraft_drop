package com.ajmal.LuxorTimeCraft.controller;

import com.ajmal.LuxorTimeCraft.dto.ProductDTO;
import com.ajmal.LuxorTimeCraft.model.Category;
import com.ajmal.LuxorTimeCraft.model.Product;
import com.ajmal.LuxorTimeCraft.model.User;
import com.ajmal.LuxorTimeCraft.repository.RoleRepository;
import com.ajmal.LuxorTimeCraft.repository.UserRepository;
import com.ajmal.LuxorTimeCraft.service.CategoryService;
import com.ajmal.LuxorTimeCraft.service.ProductService;
import com.ajmal.LuxorTimeCraft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;


    @Autowired
    RoleRepository roleRepository;

//    @GetMapping("/admin")
//    public String adminHome(){
//        return "adminHome";
//    }

    @GetMapping("/admin")
    public String getAdminHome(Model model, Principal principal){
        User user = userService.findUserByEmail(principal.getName()).get();
        String username = user.getFirstName();

        model.addAttribute("username",username);

        return "adminHome";
    }

    // Category Section
    @GetMapping("/admin/categories")
    public String getCategories(Model model){
        model.addAttribute("categories", categoryService.getAllCategory() );
        return "categories";
    }
    @GetMapping("/admin/categories/add")
    public String getCategoriesAdd(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCategoriesAdd(@ModelAttribute("category") Category category,
                                    RedirectAttributes redirectAttributes){

        boolean isExist =categoryService.existsCategory(category.getName());
        if (isExist){
            redirectAttributes.addFlashAttribute("errMsg","Category already exist !");
            return "redirect:/admin/categories";
        }

        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategories(@PathVariable int id){
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategories(@PathVariable int id, Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()){
            model.addAttribute("category",category.get());
            return "categoriesAdd";
        } else {
            return  "404";
        }
    }


    // Product Section.

    @GetMapping("/admin/products")
    public String getProducts(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }
    @GetMapping("/admin/products/add")
    public String getProductsAdd(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String postProductsAdd(@ModelAttribute("productDTO")ProductDTO productDTO,
                                  @RequestParam("productImage")MultipartFile file,
                                  @RequestParam("imgName") String imgName) throws IOException {



        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setCount(productDTO.getCount());
        product.setDescription(productDTO.getDescription());
        String imageUUID;
        if (!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){
//        categoryService.removeCategoryById(id);
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable long id, Model model){
        Product product = productService.getProductById(id).get();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setCount(product.getCount());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("productDTO", productDTO);

       return "productsAdd";
    }
    @GetMapping("/admin/users")
    public String users(Model model){
        List<User> users = userService.findAllUsers();
        model.addAttribute("users",users);
        return "users";
    }

    // block and unblock
    @GetMapping("/admin/block/{id}")
    public String blockUser(@PathVariable Integer id,
                            Principal principal,
                            RedirectAttributes redirectAttributes){
        User user = userService.findById(id).get();
        user.setActive(false);
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("successMsg","User blocked successfully!");
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/unblock/{id}")
    public String unblockUser(@PathVariable Integer id,
                              RedirectAttributes redirectAttributes
                              ){

        Optional<User> optionalUser = userService.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setActive(true);
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("successMsg","user unblocked successfully!");
            return "redirect:/admin/users";
        }else {
            return "redirect:/login";
        }

    }


}

















