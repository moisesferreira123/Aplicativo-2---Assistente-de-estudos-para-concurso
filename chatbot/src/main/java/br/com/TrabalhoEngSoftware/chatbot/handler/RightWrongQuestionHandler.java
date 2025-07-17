package br.com.TrabalhoEngSoftware.chatbot.handler;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.TrabalhoEngSoftware.chatbot.dto.RightWrongQuestionDTO;
import br.com.TrabalhoEngSoftware.chatbot.dto.RightWrongUserAnswerDTO;
import br.com.TrabalhoEngSoftware.chatbot.entity.RightWrongQuestionEntity;
import br.com.TrabalhoEngSoftwareFramework.framework.exception.InvalidObjectDataException;
import br.com.TrabalhoEngSoftwareFramework.framework.handler.FlashcardTypeHandler;

@Component
public class RightWrongQuestionHandler implements FlashcardTypeHandler<RightWrongQuestionDTO, RightWrongQuestionEntity, RightWrongUserAnswerDTO> {
  
  @Override
  public String supportsType() {
    return "RIGHT_WRONG_QUESTION";
  }

  @Override
  public RightWrongQuestionEntity createFlashcard(RightWrongQuestionDTO dto) {
    validateDTO(dto);

    RightWrongQuestionEntity rightWrongQuestion = new RightWrongQuestionEntity();
    rightWrongQuestion.setQuestion(dto.getQuestion());
    rightWrongQuestion.setRight(dto.isRight());
    rightWrongQuestion.setNextReview(LocalDateTime.now());
    rightWrongQuestion.setRepetition(0);
    rightWrongQuestion.setEaseFactor(2.5);
    rightWrongQuestion.setInterval(1);

    return rightWrongQuestion;
  }

  @Override
  public void updateFlashcard(RightWrongQuestionEntity rightWrongQuestion, RightWrongQuestionDTO dto) {
    validateDTO(dto);

    rightWrongQuestion.setQuestion(dto.getQuestion());
    rightWrongQuestion.setRight(dto.isRight());
  }

  @Override
  public void evaluateAnswer(RightWrongQuestionEntity rightWrongQuestion, RightWrongUserAnswerDTO answer) {
    // TODO: Fazer
  }

  @Override
  public RightWrongQuestionDTO entityToDTO(RightWrongQuestionEntity rightWrongQuestion) {
    RightWrongQuestionDTO dto = new RightWrongQuestionDTO();
    
    dto.setId(rightWrongQuestion.getId());
    dto.setQuestion(rightWrongQuestion.getQuestion());
    dto.setRight(rightWrongQuestion.isRight());
    dto.setCreatedAt(rightWrongQuestion.getCreatedAt());
    dto.setLastReviewedAt(rightWrongQuestion.getLastReviewedAt());
    dto.setNextReview(rightWrongQuestion.getNextReview());
    dto.setRepetition(rightWrongQuestion.getRepetition());
    dto.setEaseFactor(rightWrongQuestion.getEaseFactor());
    dto.setInterval(rightWrongQuestion.getInterval());

    return dto;
  }

  private void validateDTO(RightWrongQuestionDTO dto) {
    if(dto.getQuestion() == null || dto.getQuestion().trim().isEmpty()) {
      throw new InvalidObjectDataException("Question can't be empty");
    }
  }
}
