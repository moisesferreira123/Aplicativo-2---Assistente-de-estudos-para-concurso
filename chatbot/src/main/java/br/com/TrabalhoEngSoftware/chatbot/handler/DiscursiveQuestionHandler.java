package br.com.TrabalhoEngSoftware.chatbot.handler;

import java.time.LocalDateTime;

import br.com.TrabalhoEngSoftware.chatbot.dto.DiscursiveQuestionDTO;
import br.com.TrabalhoEngSoftware.chatbot.dto.DiscursiveUserAnswerDTO;
import br.com.TrabalhoEngSoftware.chatbot.entity.DiscursiveQuestionEntity;
import br.com.TrabalhoEngSoftwareFramework.framework.exception.InvalidObjectDataException;
import br.com.TrabalhoEngSoftwareFramework.framework.handler.FlashcardTypeHandler;
import org.springframework.stereotype.Component;

@Component 
public class DiscursiveQuestionHandler implements FlashcardTypeHandler<DiscursiveQuestionDTO, DiscursiveQuestionEntity, DiscursiveUserAnswerDTO> {

  @Override
  public String supportsType() {
    return "DISCURSIVE_QUESTION";
  }

  @Override
  public DiscursiveQuestionEntity createFlashcard(DiscursiveQuestionDTO dto) {
    validateDTO(dto);

    DiscursiveQuestionEntity discursiveQuestion = new DiscursiveQuestionEntity();
    discursiveQuestion.setQuestion(dto.getQuestion());
    discursiveQuestion.setExampleCorrectAnswer(dto.getExampleCorrectAnswer());
    discursiveQuestion.setNextReview(LocalDateTime.now()); 
    discursiveQuestion.setRepetition(0); 
    discursiveQuestion.setEaseFactor(2.5); 
    discursiveQuestion.setInterval(1);

    return discursiveQuestion;
  }

  @Override
  public void updateFlashcard(DiscursiveQuestionEntity discursiveQuestion, DiscursiveQuestionDTO dto) {
    validateDTO(dto);

    discursiveQuestion.setQuestion(dto.getQuestion());
    discursiveQuestion.setExampleCorrectAnswer(dto.getExampleCorrectAnswer());
  }

  @Override
  public void evaluateAnswer(DiscursiveQuestionEntity discursiveQuestion, DiscursiveUserAnswerDTO answer) {
    // TODO: Fazer a lógica de avaliação da resposta para questões discursivas
  }

  @Override
  public DiscursiveQuestionDTO entityToDTO(DiscursiveQuestionEntity discursiveQuestion) {
    DiscursiveQuestionDTO dto = new DiscursiveQuestionDTO();

    dto.setId(discursiveQuestion.getId());
    dto.setQuestion(discursiveQuestion.getQuestion());
    dto.setExampleCorrectAnswer(discursiveQuestion.getExampleCorrectAnswer());
    dto.setCreatedAt(discursiveQuestion.getCreatedAt());
    dto.setLastReviewedAt(discursiveQuestion.getLastReviewedAt());
    dto.setNextReview(discursiveQuestion.getNextReview());
    dto.setRepetition(discursiveQuestion.getRepetition());
    dto.setEaseFactor(discursiveQuestion.getEaseFactor());
    dto.setInterval(discursiveQuestion.getInterval());

    return dto;
  }

  private void validateDTO(DiscursiveQuestionDTO dto) {
    if (dto.getQuestion() == null || dto.getQuestion().trim().isEmpty()) {
      throw new InvalidObjectDataException("Question can't be empty");
    }

    if (dto.getExampleCorrectAnswer() == null || dto.getExampleCorrectAnswer().trim().isEmpty()) {
      throw new InvalidObjectDataException("Example correct answer can't be empty");
    }
  }
}