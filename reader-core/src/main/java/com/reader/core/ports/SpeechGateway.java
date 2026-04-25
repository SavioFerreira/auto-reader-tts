package com.reader.core.ports;

import com.reader.core.model.VoiceConfiguration;

public interface SpeechGateway {

    byte[] synthesize(String normalizedWord);

    String getAudioContentType();

    VoiceConfiguration getVoiceConfiguration();
}

