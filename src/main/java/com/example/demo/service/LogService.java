package com.example.demo.service;

import com.example.demo.document.Log;
import com.example.demo.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LogService {
    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<Log> getAllLogs(){
        return StreamSupport.stream(logRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}

