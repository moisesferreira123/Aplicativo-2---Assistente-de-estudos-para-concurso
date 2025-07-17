package br.com.TrabalhoEngSoftware.chatbot.handler;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

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
    // TODO: Fazer
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