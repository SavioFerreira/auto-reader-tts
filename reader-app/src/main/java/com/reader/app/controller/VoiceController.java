package com.reader.app.controller;

import com.reader.app.dto.response.VoiceResponse;
import com.reader.core.service.WordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voice")
public class VoiceController {

    private final WordService wordService;

    public VoiceController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping
    public VoiceResponse voice() {
        var voiceConfiguration = wordService.getVoiceConfiguration();
        return new VoiceResponse(
                voiceConfiguration.locale(),
                voiceConfiguration.voice(),
                voiceConfiguration.embedded()
        );
    }
}
