package br.com.TrabalhoEngSoftware.chatbot.handler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import br.com.TrabalhoEngSoftware.chatbot.config.Constants;
import br.com.TrabalhoEngSoftware.chatbot.dto.MultipleAnswersQuestionDTO;
import br.com.TrabalhoEngSoftware.chatbot.dto.MultipleAnswersUserAnswerDTO;
import br.com.TrabalhoEngSoftware.chatbot.entity.MultipleAnswersQuestionEntity;
import br.com.TrabalhoEngSoftwareFramework.framework.exception.InvalidObjectDataException;
import br.com.TrabalhoEngSoftwareFramework.framework.handler.FlashcardTypeHandler;

@Component
public class MultipleAnswersQuestionHandler implements FlashcardTypeHandler<MultipleAnswersQuestionDTO, MultipleAnswersQuestionEntity, MultipleAnswersUserAnswerDTO> {

  @Override
  public String supportsType() {
    return "MULTIPLE_ANSWERS_QUESTION";
  }

  @Override
  public MultipleAnswersQuestionEntity createFlashcard(MultipleAnswersQuestionDTO dto) {
    validateDTO(dto);

    MultipleAnswersQuestionEntity multipleAnswersQuestion = new MultipleAnswersQuestionEntity();
    multipleAnswersQuestion.setQuestion(dto.getQuestion());
    multipleAnswersQuestion.setAnswerOptions(dto.getAnswerOptions());
    multipleAnswersQuestion.setCorrectAnswerIndices(dto.getCorrectAnswerIndices()); 
    multipleAnswersQuestion.setNextReview(LocalDateTime.now());
    multipleAnswersQuestion.setRepetition(0);
    multipleAnswersQuestion.setEaseFactor(2.5);
    multipleAnswersQuestion.setInterval(1);

    return multipleAnswersQuestion;
  }

  @Override
  public void updateFlashcard(MultipleAnswersQuestionEntity multipleAnswersQuestion, MultipleAnswersQuestionDTO dto) {
    validateDTO(dto);

    multipleAnswersQuestion.setQuestion(dto.getQuestion());
    multipleAnswersQuestion.setAnswerOptions(dto.getAnswerOptions());
    multipleAnswersQuestion.setCorrectAnswerIndices(dto.getCorrectAnswerIndices());
  }

  @Override
  public void evaluateAnswer(MultipleAnswersQuestionEntity multipleAnswersQuestion, MultipleAnswersUserAnswerDTO answer) {
    for (Integer index : answer.getOptionsChosen()) {
      if (index < 0 || index >= multipleAnswersQuestion.getAnswerOptions().size()) {
        throw new InvalidObjectDataException("Correct answer index " + index + " is out of bounds for the provided options.");
      }
    }

    LocalDateTime tomorrow = LocalDate.now().plusDays(1).atStartOfDay();
    double correctAnswers = 0, wrongAnswers = 0;
    for(Integer userAnswerIndex: answer.getOptionsChosen()) {
      if(multipleAnswersQuestion.getCorrectAnswerIndices().contains(userAnswerIndex)) {
        correctAnswers++;
      } else {
        wrongAnswers++;
      }
    }

    wrongAnswers += multipleAnswersQuestion.getCorrectAnswerIndices().size()-correctAnswers;
    correctAnswers = multipleAnswersQuestion.getAnswerOptions().size() - wrongAnswers;
    
    double correctPercentage = correctAnswers/multipleAnswersQuestion.getAnswerOptions().size();
    double easeFactor = multipleAnswersQuestion.getEaseFactor();

    if(correctPercentage <= 0.3) {
      if(LocalDateTime.now().plusMinutes(60L).isBefore(tomorrow)){
        multipleAnswersQuestion.setNextReview(LocalDateTime.now().plusMinutes(60L));
      } else {
        multipleAnswersQuestion.setNextReview(LocalDate.now().atTime(LocalTime.MAX));
      }
      multipleAnswersQuestion.setEaseFactor(calculateEaseFactor(easeFactor, Constants.WRONG));
      multipleAnswersQuestion.setInterval(1);
      return;
    }
    multipleAnswersQuestion.setNextReview(LocalDateTime.now().plusDays(multipleAnswersQuestion.getInterval()));
    if(correctPercentage < 0.7) {
      multipleAnswersQuestion.setEaseFactor(calculateEaseFactor(easeFactor, Constants.HARD));
    } else if(correctPercentage < 1) {
      multipleAnswersQuestion.setEaseFactor(calculateEaseFactor(easeFactor, Constants.GOOD));
    } else {
      multipleAnswersQuestion.setEaseFactor(calculateEaseFactor(easeFactor, Constants.EASY));
    }
    multipleAnswersQuestion.setInterval((int) Math.ceil(multipleAnswersQuestion.getInterval()*multipleAnswersQuestion.getEaseFactor()));
  }

  private double calculateEaseFactor(double easeFactor, int answer) {
    double easeFactorTemp = easeFactor - 0.8 + (0.28*answer) - (0.02*Math.pow(answer,2));
    return Math.max(Constants.MIN_EASE_FACTOR, easeFactorTemp);
  }

  @Override
  public MultipleAnswersQuestionDTO entityToDTO(MultipleAnswersQuestionEntity multipleAnswersQuestion) {
    MultipleAnswersQuestionDTO dto = new MultipleAnswersQuestionDTO();

    dto.setId(multipleAnswersQuestion.getId());
    dto.setQuestion(multipleAnswersQuestion.getQuestion());
    dto.setAnswerOptions(multipleAnswersQuestion.getAnswerOptions());
    dto.setCorrectAnswerIndices(multipleAnswersQuestion.getCorrectAnswerIndices());
    dto.setCreatedAt(multipleAnswersQuestion.getCreatedAt());
    dto.setLastReviewedAt(multipleAnswersQuestion.getLastReviewedAt());
    dto.setNextReview(multipleAnswersQuestion.getNextReview());
    dto.setRepetition(multipleAnswersQuestion.getRepetition());
    dto.setEaseFactor(multipleAnswersQuestion.getEaseFactor());
    dto.setInterval(multipleAnswersQuestion.getInterval());

    return dto;
  }

  private void validateDTO(MultipleAnswersQuestionDTO dto) {
    if (dto.getQuestion() == null || dto.getQuestion().trim().isEmpty()) {
      throw new InvalidObjectDataException("Question can't be empty");
    }

    if (dto.getAnswerOptions().isEmpty()) {
      throw new InvalidObjectDataException("List of options can't be empty");
    }

    if (dto.getCorrectAnswerIndices().isEmpty()) {
      throw new InvalidObjectDataException("List of correct answer indices can't be empty");
    }

    for (String option : dto.getAnswerOptions()) {
      if (option == null || option.trim().isEmpty()) {
        throw new InvalidObjectDataException("No option in the list can be empty");
      }
    }

    for (Integer index : dto.getCorrectAnswerIndices()) {
      if (index < 0 || index >= dto.getAnswerOptions().size()) {
        throw new InvalidObjectDataException("Correct answer index " + index + " is out of bounds for the provided options.");
      }
    }
  }
}