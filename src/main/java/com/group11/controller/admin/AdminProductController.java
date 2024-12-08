package com.group11.controller.admin;

import com.group11.entity.*;
import com.group11.service.*;
import com.group11.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    IProductService productService = new ProductServiceImpl();

    @Autowired
    IProductDetailService productDetailService = new ProductDetailServiceImpl();
    @Autowired
    IInventoryService inventoryService = new InventoryServiceImpl();

    @Autowired
    IManufacturerService manufacturerService = new ManufacturerServiceImpl();

    @Autowired
    ICategoryService categoryService = new CategoryServiceImpl();




    @GetMapping
    public String load()
    {
        return "admin-products";
    }
    @GetMapping("/add1")
    public String them()
    {
        return "add";
    }

    @GetMapping("/add")
    public String editProductForm(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("manufacturers", manufacturerService.findAll());
        return "admin/admin-product-add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductEntity productEntity, @RequestParam("ram") String ram,
                             @RequestParam("cpu") String cpu, @RequestParam("gpu") String gpu, @RequestParam("monitor") String monitor,
                             @RequestParam("color") String color, @RequestParam("wifi") String wifi, @RequestParam("lan") String lan,
                             @RequestParam("audio") String audio, @RequestParam("bluetooth") String bluetooth,
                             @RequestParam("charger") String charger, @RequestParam("connect") String connect,
                             @RequestParam("disk") String disk, @RequestParam("size") String size, @RequestParam("webcam") String webcam,
                             @RequestParam("weight") String weight, @RequestParam("operationSystem") String operationSystem,
                             @RequestParam("description") String description,
                             @RequestParam("imageURI[]") List<String> imageURIs, RedirectAttributes redirectAttributes) {

        try {
            // Tạo ProductDetailEntity và liên kết với ProductEntity
            ProductDetailEntity detail = new ProductDetailEntity();
            detail.setRAM(ram);
            detail.setCPU(cpu);
            detail.setGPU(gpu);
            detail.setWIFI(wifi);
            detail.setMonitor(monitor);
            detail.setColor(color);
            detail.setLAN(lan);
            detail.setAudio(audio);
            detail.setBluetooth(bluetooth);
            detail.setCharger(charger);
            detail.setConnect(connect);
            detail.setDisk(disk);
            detail.setSize(size);
            detail.setWebcam(webcam);
            detail.setWeight(weight);
            detail.setOperationSystem(operationSystem);
            detail.setDescription(description);


            // Xử lý danh sách ảnh
            List<ImageItemEntity> imageItems = new ArrayList<>();
            for (int i = 0; i < imageURIs.size(); i++) {
                ImageItemEntity imageItem = new ImageItemEntity();
                imageItem.setImageUrl(imageURIs.get(i));
                imageItem.setProductDetail(detail); // Liên kết với ProductDetailEntity
                imageItems.add(imageItem);
            }
            detail.setImages(imageItems);

            // Lưu ProductDetailEntity vào database trước
            productDetailService.save(detail); // Lưu ProductDetailEntity vào database

            productEntity.setDetail(detail);

            // Lưu ProductEntity vào database
            productService.save(productEntity);
            InventoryEntity inventory = new InventoryEntity();
            inventory.setProductId(productEntity.getProductID());
            if(productEntity.getStatus().equals(ProductStatus.AVAILABLE))
            {
                inventory.setQuantity(1);
            }
            else {
                inventory.setQuantity(0);
            }

            inventoryService.save(inventory);

            // Lưu InventoryEntity vào database
            redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add product: " + e.getMessage());
        }

        return "redirect:/admin/products";
    }
}
