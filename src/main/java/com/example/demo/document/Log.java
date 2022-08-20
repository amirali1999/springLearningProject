package com.example.demo.document;

import com.example.demo.helper.Indices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Document(indexName = Indices.LOG_INDEX)
@Setting(settingPath = "static/es-settings.json")
@Getter
@Setter
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Date)
    private String date;
    @Field(type = FieldType.Text)
    private String logDescription;

    public Log(String date, String logDescription) {
        this.date = date;
        this.logDescription = logDescription;
    }
}
