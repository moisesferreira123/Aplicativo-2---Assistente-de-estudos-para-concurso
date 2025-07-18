package br.com.TrabalhoEngSoftware.chatbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.TrabalhoEngSoftware.chatbot.dto.DiscursiveQuestionDTO;
import br.com.TrabalhoEngSoftware.chatbot.dto.MultipleAnswersQuestionDTO;
import br.com.TrabalhoEngSoftware.chatbot.dto.MultipleChoiceQuestionDTO;
import br.com.TrabalhoEngSoftware.chatbot.dto.RightWrongQuestionDTO;
import br.com.TrabalhoEngSoftware.chatbot.dto.StandardFlashcardDTO;
import br.com.TrabalhoEngSoftware.chatbot.dto.StandardUserAnswerDTO;
import br.com.TrabalhoEngSoftware.chatbot.dto.TrueFalseQuestionDTO;

@Configuration
public class JacksonConfig {

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();

    module.registerSubtypes(
      new NamedType(StandardFlashcardDTO.class, "STANDARD_FLASHCARD"),
      new NamedType(TrueFalseQuestionDTO.class, "TRUE_FALSE_QUESTION"),
      new NamedType(RightWrongQuestionDTO.class, "RIGHT_WRONG_QUESTION"),
      new NamedType(MultipleChoiceQuestionDTO.class, "MULTIPLE_CHOICE_QUESTION"),
      new NamedType(MultipleAnswersQuestionDTO.class, "MULTIPLE_ANSWERS_QUESTION"),
      new NamedType(DiscursiveQuestionDTO.class, "DISCURSIVE_QUESTION")
    );

    module.registerSubtypes(
      new NamedType(StandardUserAnswerDTO.class, "STANDARD_FLASHCARD"),
      new NamedType(TrueFalseQuestionDTO.class, "TRUE_FALSE_QUESTION"),
      new NamedType(RightWrongQuestionDTO.class, "RIGHT_WRONG_QUESTION"),
      new NamedType(MultipleChoiceQuestionDTO.class, "MULTIPLE_CHOICE_QUESTION"),
      new NamedType(MultipleAnswersQuestionDTO.class, "MULTIPLE_ANSWERS_QUESTION"),
      new NamedType(DiscursiveQuestionDTO.class, "DISCURSIVE_QUESTION")
    );

    objectMapper.registerModule(module);

    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    return objectMapper;
  }
}
