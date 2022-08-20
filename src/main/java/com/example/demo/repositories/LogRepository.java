package com.example.demo.repositories;

import com.example.demo.document.Log;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogRepository extends ElasticsearchRepository<Log,String> {
}
