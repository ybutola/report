package com.butola.report.listener;

import com.butola.report.service.worddoc.WriteToWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StartupListener {
    @Autowired
    WriteToWord writeToWord;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        try {
            writeToWord.writeToWord();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
