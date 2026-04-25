package com.reader.app.controller;

import com.reader.app.exception.ApiExceptionHandler;
import com.reader.core.model.VoiceConfiguration;
import com.reader.core.model.WordReadResult;
import com.reader.core.service.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WordControllerTest {

    @Test
    void shouldReturnWavAudio() throws Exception {
        WordService wordService = mock(WordService.class);
        byte[] audioBytes = new byte[]{1, 2, 3, 4};

        when(wordService.readWord("hello")).thenReturn(new WordReadResult(
                "hello",
                "hello",
                "hello",
                true,
                true,
                "audio/wav",
                audioBytes,
                new VoiceConfiguration("en_US", "cmu-slt-hsmm", true)
        ));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WordController(wordService))
                .setControllerAdvice(new ApiExceptionHandler())
                .build();

        mockMvc.perform(post("/api/words/read/audio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "word": "hello"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentType("audio/wav"))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"hello.wav\""))
                .andExpect(content().bytes(audioBytes));
    }
}
