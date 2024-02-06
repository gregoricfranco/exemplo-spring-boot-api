package com.example.demo.service;

import com.example.demo.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagemService {


    private ImagemRepository imagemRepository;

    @Autowired
    public ImagemService(ImagemRepository imagemRepository) {
        this.imagemRepository = imagemRepository;
    }





}
