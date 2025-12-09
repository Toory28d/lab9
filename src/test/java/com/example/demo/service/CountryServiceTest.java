package com.example.demo.service;

import com.example.demo.dto.CountryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Test
    void shouldLoadAllSeededCountries() {
        List<CountryDto> countries = countryService.findAll();
        assertThat(countries).hasSize(4);
        assertThat(countries).extracting(CountryDto::getName)
                .containsExactlyInAnyOrder("United States", "China", "Germany", "Japan");
    }

    @Test
    void shouldFindById() {
        CountryDto dto = countryService.findById(1L);
        assertThat(dto.getName()).isEqualTo("United States");
    }

    @Test
    @Transactional
    void shouldCreateNewCountry() {
        CountryDto newDto = new CountryDto(null, "Russia", "RUS");
        CountryDto saved = countryService.create(newDto);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Russia");
    }

    @Test
    @Transactional
    void shouldUpdateCountry() {
        CountryDto updatedDto = new CountryDto(1L, "United States of America", "USA");
        CountryDto result = countryService.update(1L, updatedDto);
        assertThat(result.getName()).isEqualTo("United States of America");
    }

    @Test
    @Transactional
    void shouldDeleteCountry() {
        long initialSize = countryService.findAll().size();
        countryService.deleteById(4L);
        assertThat(countryService.findAll()).hasSize((int) initialSize - 1);
    }
}