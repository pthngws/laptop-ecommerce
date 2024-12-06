package com.group11.controller.admin;

import com.group11.dto.RateResponse;
import com.group11.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/rates")
public class AdminResponseController {

    @Autowired
    private IRateService rateService;

    @GetMapping
    public String viewRatesPage(Model model) {
        List<RateResponse> rates = rateService.getAllRates();
        model.addAttribute("rates", rates);
        return "rateresponse"; // Trả về file admin_rates.html trong thư mục templates
    }
}
