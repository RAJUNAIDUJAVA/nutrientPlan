package com.dnapass.training.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DownStreamService {
    public Optional<Object> getData() {

        return Optional.ofNullable("RajuNaidu");
    }
}
