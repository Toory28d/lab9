package com.example.demo.controller;

import com.example.demo.dto.CountryDto;
import com.example.demo.service.CountryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/countries")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("countries", countryService.findAll());
        return "country/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("country", new CountryDto(null, "", ""));
        return "country/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        CountryDto dto = countryService.findById(id);
        model.addAttribute("country", dto);
        return "country/form";
    }

    @PostMapping
    public String save(@ModelAttribute CountryDto dto) {
        if (dto.getId() == null) {
            countryService.create(dto);
        } else {
            countryService.update(dto.getId(), dto);
        }
        return "redirect:/countries";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        countryService.deleteById(id);
        return "redirect:/countries";
    }
}