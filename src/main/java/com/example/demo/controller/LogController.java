package com.example.demo.controller;

import com.example.demo.document.Log;
import com.example.demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/log")
public class LogController {
    private final LogService logService;

    @Autowired
    public LogController(LogService service) {
        this.logService = service;
    }

    @GetMapping("/getall")
    public List<Log> getAllLogs(){
        return logService.getAllLogs();
    }
//    @PostMapping
//    public void save(@RequestBody final Log log){
//        logService.save(log);
//    }

//    @GetMapping("/{id}")
//    public Log findById(@PathVariable final String id){
//        return logService.findByService(id);
//    }
}
