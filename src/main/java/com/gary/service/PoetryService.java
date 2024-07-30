package com.gary.service;

import com.gary.dto.PoetryDto;
import org.springframework.stereotype.Service;

public interface PoetryService {
    String getCathaiku();

    PoetryDto getPoetryGetGenreAndTheme(String genre, String theme);
}
