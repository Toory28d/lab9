package com.example.demo.controller;

import com.example.demo.dto.ItemRequestDto;
import com.example.demo.dto.ItemResponseDto;
import com.example.demo.service.CountryService;
import com.example.demo.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final CountryService countryService;

    public ItemController(ItemService itemService, CountryService countryService) {
        this.itemService = itemService;
        this.countryService = countryService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "item/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("item", new ItemRequestDto());
        model.addAttribute("countries", countryService.findAll());
        return "item/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ItemResponseDto resp = itemService.findById(id);
        ItemRequestDto req = new ItemRequestDto(resp.getId(), resp.getName(), resp.getPrice(), resp.getQuantity(), resp.getManufacturer().getId());
        model.addAttribute("item", req);
        model.addAttribute("countries", countryService.findAll());
        return "item/form";
    }

    @PostMapping
    public String save(@ModelAttribute ItemRequestDto dto) {
        if (dto.getId() == null) {
            itemService.create(dto);
        } else {
            itemService.update(dto.getId(), dto);
        }
        return "redirect:/items";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        itemService.deleteById(id);
        return "redirect:/items";
    }
}