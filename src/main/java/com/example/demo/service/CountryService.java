// CountryService.java
package com.example.demo.service;

import com.example.demo.dto.CountryDto;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.models.Country;
import com.example.demo.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final ShopMapper mapper;

    public List<CountryDto> findAll() {
        return countryRepository.findAll().stream()
                .map(mapper::toDto)  // ← теперь метод существует!
                .toList();
    }

    public CountryDto findById(Long id) {
        return mapper.toDto(countryRepository.findById(id).orElseThrow());
    }

    public CountryDto save(CountryDto dto) {
        Country country = mapper.toEntity(dto);
        return mapper.toDto(countryRepository.save(country));
    }

    public CountryDto update(CountryDto dto) {
        Country country = countryRepository.findById(dto.getId()).orElseThrow();
        mapper.updateFromDto(dto, country);
        return mapper.toDto(countryRepository.save(country));
    }

    public void deleteById(Long id) {
        countryRepository.deleteById(id);
    }
}