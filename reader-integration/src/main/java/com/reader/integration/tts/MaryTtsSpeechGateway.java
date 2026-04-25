package com.reader.integration.tts;

import com.reader.core.exception.SpeechSynthesisException;
import com.reader.core.model.VoiceConfiguration;
import com.reader.core.ports.SpeechGateway;
import com.reader.integration.config.TtsSettings;
import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

public class MaryTtsSpeechGateway implements SpeechGateway {

    private static final String AUDIO_CONTENT_TYPE = "audio/wav";

    private final MaryInterface maryInterface;
    private final VoiceConfiguration voiceConfiguration;

    public MaryTtsSpeechGateway(TtsSettings settings) {
        try {
            Locale locale = Locale.forLanguageTag(settings.locale().replace('_', '-'));
            MaryInterface configuredMary = new LocalMaryInterface();
            configuredMary.setLocale(locale);
            configuredMary.setVoice(settings.voice());

            this.maryInterface = configuredMary;
            this.voiceConfiguration = new VoiceConfiguration(settings.locale(), settings.voice(), true);
        } catch (MaryConfigurationException | IllegalArgumentException exception) {
            throw new IllegalStateException("Failed to initialize embedded MaryTTS.", exception);
        }
    }

    @Override
    public synchronized byte[] synthesize(String normalizedWord) {
        try (AudioInputStream audio = maryInterface.generateAudio(normalizedWord);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            AudioSystem.write(audio, AudioFileFormat.Type.WAVE, outputStream);
            return outputStream.toByteArray();
        } catch (SynthesisException | IOException exception) {
            throw new SpeechSynthesisException("Failed to synthesize audio with MaryTTS.", exception);
        }
    }

    @Override
    public String getAudioContentType() {
        return AUDIO_CONTENT_TYPE;
    }

    @Override
    public VoiceConfiguration getVoiceConfiguration() {
        return voiceConfiguration;
    }
}

