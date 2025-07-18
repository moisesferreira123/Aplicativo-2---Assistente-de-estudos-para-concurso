package br.com.TrabalhoEngSoftware.chatbot.handler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import br.com.TrabalhoEngSoftware.chatbot.config.Constants;
import br.com.TrabalhoEngSoftware.chatbot.dto.MultipleChoiceQuestionDTO;
import br.com.TrabalhoEngSoftware.chatbot.dto.MultipleChoiceUserAnswerDTO;
import br.com.TrabalhoEngSoftware.chatbot.entity.MultipleChoiceQuestionEntity;
import br.com.TrabalhoEngSoftwareFramework.framework.exception.InvalidObjectDataException;
import br.com.TrabalhoEngSoftwareFramework.framework.handler.FlashcardTypeHandler;

@Component 
public class MultipleChoiceQuestionHandler implements FlashcardTypeHandler<MultipleChoiceQuestionDTO, MultipleChoiceQuestionEntity, MultipleChoiceUserAnswerDTO> {
  
  @Override
  public String supportsType() {
    return "MULTIPLE_CHOICE_QUESTION";
  }

  @Override
  public MultipleChoiceQuestionEntity createFlashcard(MultipleChoiceQuestionDTO dto) {
    validateDTO(dto);

    MultipleChoiceQuestionEntity multipleChoiceQuestion = new MultipleChoiceQuestionEntity();
    multipleChoiceQuestion.setQuestion(dto.getQuestion());
    multipleChoiceQuestion.setAnswerOptions(dto.getAnswerOptions());
    multipleChoiceQuestion.setCorrectAnswerIndex(dto.getCorrectAnswerIndex());
    multipleChoiceQuestion.setNextReview(LocalDateTime.now());
    multipleChoiceQuestion.setRepetition(0);
    multipleChoiceQuestion.setEaseFactor(2.5);
    multipleChoiceQuestion.setInterval(1);

    return multipleChoiceQuestion;
  }

  @Override
  public void updateFlashcard(MultipleChoiceQuestionEntity multipleChoiceQuestion, MultipleChoiceQuestionDTO dto) {
    validateDTO(dto);

    multipleChoiceQuestion.setQuestion(dto.getQuestion());
    multipleChoiceQuestion.setAnswerOptions(dto.getAnswerOptions());
    multipleChoiceQuestion.setCorrectAnswerIndex(dto.getCorrectAnswerIndex());
  }

  @Override
  public void evaluateAnswer(MultipleChoiceQuestionEntity multipleChoiceQuestion, MultipleChoiceUserAnswerDTO answer) {
    LocalDateTime tomorrow = LocalDate.now().plusDays(1).atStartOfDay();
    double easeFactor = multipleChoiceQuestion.getEaseFactor();

    if(multipleChoiceQuestion.getCorrectAnswerIndex() == answer.getAnswer()) {
      if(LocalDateTime.now().plusMinutes(60L).isBefore(tomorrow)){
        multipleChoiceQuestion.setNextReview(LocalDateTime.now().plusMinutes(60L));
      } else {
        multipleChoiceQuestion.setNextReview(LocalDate.now().atTime(LocalTime.MAX));
      }
      multipleChoiceQuestion.setEaseFactor(calculateEaseFactor(easeFactor, Constants.WRONG));
      multipleChoiceQuestion.setInterval(1);
    } else {
      multipleChoiceQuestion.setNextReview(LocalDateTime.now().plusDays(multipleChoiceQuestion.getInterval()));
      multipleChoiceQuestion.setEaseFactor(calculateEaseFactor(easeFactor, Constants.GOOD));
      multipleChoiceQuestion.setInterval((int) Math.ceil(multipleChoiceQuestion.getInterval()*multipleChoiceQuestion.getEaseFactor()));
    }
  }

  private double calculateEaseFactor(double easeFactor, int answer) {
    double easeFactorTemp = easeFactor - 0.8 + (0.28*answer) - (0.02*Math.pow(answer,2));
    return Math.max(Constants.MIN_EASE_FACTOR, easeFactorTemp);
  }

  @Override
  public MultipleChoiceQuestionDTO entityToDTO(MultipleChoiceQuestionEntity multipleChoiceQuestion) {
    MultipleChoiceQuestionDTO dto = new MultipleChoiceQuestionDTO();

    dto.setId(multipleChoiceQuestion.getId());
    dto.setQuestion(multipleChoiceQuestion.getQuestion());
    dto.setAnswerOptions(multipleChoiceQuestion.getAnswerOptions());
    dto.setCorrectAnswerIndex(multipleChoiceQuestion.getCorrectAnswerIndex());
    dto.setCreatedAt(multipleChoiceQuestion.getCreatedAt());
    dto.setLastReviewedAt(multipleChoiceQuestion.getLastReviewedAt());
    dto.setNextReview(multipleChoiceQuestion.getNextReview());
    dto.setRepetition(multipleChoiceQuestion.getRepetition());
    dto.setEaseFactor(multipleChoiceQuestion.getEaseFactor());
    dto.setInterval(multipleChoiceQuestion.getInterval());

    return dto;
  }

  private void validateDTO(MultipleChoiceQuestionDTO dto) {
    if(dto.getQuestion() == null || dto.getQuestion().trim().isEmpty()) {
      throw new InvalidObjectDataException("Question can't be empty");
    }

    if(dto.getAnswerOptions().isEmpty()) {
      throw new InvalidObjectDataException("List of options can't be empty");
    }

    for(String option : dto.getAnswerOptions()) {
      if(option == null || option.trim().isEmpty()) {
        throw new InvalidObjectDataException("No answer option in the list can be empty");
      }
    }

    if(dto.getCorrectAnswerIndex() < 0 || dto.getCorrectAnswerIndex() >= dto.getAnswerOptions().size()) {
      throw new InvalidObjectDataException("Correct answer index " + dto.getCorrectAnswerIndex() + " is out of bounds for the provided options.");
    }
  }
}
