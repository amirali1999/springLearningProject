package com.example.demo.config.quartz.info;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimerInfo {
    private int totalFireCount;
    private boolean runForever;
    private long repeatIntervslMs;
    private long initialOffsetms;
    private String callbackData;
}
