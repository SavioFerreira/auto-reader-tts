package com.reader.app.controller;

import com.reader.app.dto.request.WordRequest;
import com.reader.app.dto.response.WordReadResponse;
import com.reader.app.dto.response.WordValidationResponse;
import com.reader.core.service.WordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/api/words")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping("/validate")
    public WordValidationResponse validate(@Valid @RequestBody WordRequest request) {
        var validation = wordService.validateWord(request.word());
        return new WordValidationResponse(
                validation.originalWord(),
                validation.normalizedWord(),
                validation.readableText(),
                validation.existsInEnglish(),
                validation.readyToRead()
        );
    }

    @PostMapping("/read")
    public WordReadResponse read(@Valid @RequestBody WordRequest request) {
        var readResult = wordService.readWord(request.word());
        return new WordReadResponse(
                readResult.originalWord(),
                readResult.normalizedWord(),
                readResult.readableText(),
                readResult.existsInEnglish(),
                readResult.readyToRead(),
                readResult.voiceConfiguration().locale(),
                readResult.voiceConfiguration().voice(),
                readResult.contentType(),
                Base64.getEncoder().encodeToString(readResult.audioBytes())
        );
    }

    @PostMapping(value = "/read/audio", produces = "audio/wav")
    public ResponseEntity<byte[]> readAudio(@Valid @RequestBody WordRequest request) {
        var readResult = wordService.readWord(request.word());
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(readResult.contentType()));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + readResult.normalizedWord() + ".wav\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(readResult.audioBytes().length)
                .body(readResult.audioBytes());
    }
}
