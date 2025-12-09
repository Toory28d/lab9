package com.example.demo.service;

import com.example.demo.dto.ItemRequestDto;
import com.example.demo.dto.ItemResponseDto;
import com.example.demo.entity.Country;
import com.example.demo.entity.Item;
import com.example.demo.mapper.ItemMapper;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CountryRepository countryRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, CountryRepository countryRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.countryRepository = countryRepository;
        this.itemMapper = itemMapper;
    }

    public List<ItemResponseDto> findAll() {
        return itemMapper.toResponseDtoList(itemRepository.findAll());
    }

    public ItemResponseDto findById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + id));
        return itemMapper.toResponseDto(item);
    }

    public ItemResponseDto create(ItemRequestDto dto) {
        Item item = itemMapper.toEntity(dto);
        Country country = countryRepository.findById(dto.getManufacturerId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found: " + dto.getManufacturerId()));
        item.setManufacturer(country);
        item = itemRepository.save(item);
        return itemMapper.toResponseDto(item);
    }

    public ItemResponseDto update(Long id, ItemRequestDto dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + id));
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        Country country = countryRepository.findById(dto.getManufacturerId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));
        item.setManufacturer(country);
        item = itemRepository.save(item);
        return itemMapper.toResponseDto(item);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
}