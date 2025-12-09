package com.example.demo.service;

import com.example.demo.dto.CountryDto;
import com.example.demo.entity.Country;
import com.example.demo.mapper.CountryMapper;
import com.example.demo.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    public List<CountryDto> findAll() {
        return countryMapper.toDtoList(countryRepository.findAll());
    }

    public CountryDto findById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Country not found: " + id));
        return countryMapper.toDto(country);
    }

    public CountryDto create(CountryDto dto) {
        Country country = countryMapper.toEntity(dto);
        country = countryRepository.save(country);
        return countryMapper.toDto(country);
    }

    public CountryDto update(Long id, CountryDto dto) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Country not found: " + id));
        country.setName(dto.getName());
        country.setCode(dto.getCode());
        country = countryRepository.save(country);
        return countryMapper.toDto(country);
    }

    public void deleteById(Long id) {
        countryRepository.deleteById(id);
    }
}