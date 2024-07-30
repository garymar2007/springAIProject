package com.gary.service.impl;

import com.gary.dto.PoetryDto;
import com.gary.service.PoetryService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PoetryServiceImpl implements PoetryService {
    public static final String WRITE_ME_HAIKU_ABOUT_CAT = """
           Write me Haiku about cat,
           haiku should start with the word cat obligatory""";
    private final ChatModel aiClient;

    @Autowired
    public PoetryServiceImpl(@Qualifier("openAiChatModel") ChatModel aiClient) {
        this.aiClient = aiClient;
    }
    public String getCathaiku() {
        return aiClient.call(WRITE_ME_HAIKU_ABOUT_CAT);
    }

    @Override
    public PoetryDto getPoetryGetGenreAndTheme(String genre, String theme) {
        BeanOutputConverter<PoetryDto> converter = new BeanOutputConverter<>(PoetryDto.class);
        String promptString = """
                Write me a {genre} poem about {theme}
                {format}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(promptString);
        promptTemplate.add("genre", genre);
        promptTemplate.add("theme", theme);
        promptTemplate.add("format", converter.getFormat());

        ChatResponse response = aiClient.call(promptTemplate.create());
        return converter.convert(response.getResult().getOutput().getContent());
    }
}
