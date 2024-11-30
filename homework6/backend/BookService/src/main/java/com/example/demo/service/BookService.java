package com.example.demo.service;


import com.example.demo.model.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BookService {

    private ObjectMapper objectMapper;
    private ResourceLoader resourceLoader;

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    public BookService(ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }

    public List<Book> getBooks() throws IOException {
        logger.info("Loading Resource and getting books...");
        Resource resource = resourceLoader.getResource("classpath:books.json");
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Book>>() {});
    }
}
