package br.com.TrabalhoEngSoftware.chatbot.handler;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.TrabalhoEngSoftware.chatbot.dto.TrueFalseQuestionDTO;
import br.com.TrabalhoEngSoftware.chatbot.dto.TrueFalseUserAnswerDTO;
import br.com.TrabalhoEngSoftware.chatbot.entity.TrueFalseQuestionEntity;
import br.com.TrabalhoEngSoftwareFramework.framework.exception.InvalidObjectDataException;
import br.com.TrabalhoEngSoftwareFramework.framework.handler.FlashcardTypeHandler;

@Component 
public class TrueFalseQuestionHandler implements FlashcardTypeHandler<TrueFalseQuestionDTO, TrueFalseQuestionEntity, TrueFalseUserAnswerDTO> {
  
  @Override
  public String supportsType() {
    return "TRUE_FALSE_QUESTION";
  }

  @Override
  public TrueFalseQuestionEntity createFlashcard(TrueFalseQuestionDTO dto) {
    validateDTO(dto);

    TrueFalseQuestionEntity trueFalseQuestion = new TrueFalseQuestionEntity();
    trueFalseQuestion.setQuestion(dto.getQuestion());
    trueFalseQuestion.setStatements(dto.getStatements());
    trueFalseQuestion.setTrueFalseAnswers(dto.getTrueFalseAnswers());
    trueFalseQuestion.setNextReview(LocalDateTime.now());
    trueFalseQuestion.setRepetition(0);
    trueFalseQuestion.setEaseFactor(2.5);
    trueFalseQuestion.setInterval(1);

    return trueFalseQuestion;
  }

  @Override
  public void updateFlashcard(TrueFalseQuestionEntity trueFalseQuestion, TrueFalseQuestionDTO dto) {
    validateDTO(dto);

    trueFalseQuestion.setQuestion(dto.getQuestion());
    trueFalseQuestion.setStatements(dto.getStatements());
    trueFalseQuestion.setTrueFalseAnswers(dto.getTrueFalseAnswers());
  }

  @Override
  public void evaluateAnswer(TrueFalseQuestionEntity trueFalseQuestion, TrueFalseUserAnswerDTO answer) {
    // TODO: Fazer
  }

  @Override
  public TrueFalseQuestionDTO entityToDTO(TrueFalseQuestionEntity trueFalseQuestion) {
    TrueFalseQuestionDTO dto = new TrueFalseQuestionDTO();
    
    dto.setId(trueFalseQuestion.getId());
    dto.setQuestion(trueFalseQuestion.getQuestion());
    dto.setStatements(trueFalseQuestion.getStatements());
    dto.setTrueFalseAnswers(trueFalseQuestion.getTrueFalseAnswers());
    dto.setCreatedAt(trueFalseQuestion.getCreatedAt());
    dto.setLastReviewedAt(trueFalseQuestion.getLastReviewedAt());
    dto.setNextReview(trueFalseQuestion.getNextReview());
    dto.setRepetition(trueFalseQuestion.getRepetition());
    dto.setEaseFactor(trueFalseQuestion.getEaseFactor());
    dto.setInterval(trueFalseQuestion.getInterval());
    
    return dto;
  }

  private void validateDTO(TrueFalseQuestionDTO dto) {
    if(dto.getQuestion() == null || dto.getQuestion().trim().isEmpty()) {
      throw new InvalidObjectDataException("Question can't be empty");
    }

    if(dto.getStatements().isEmpty()) {
      throw new InvalidObjectDataException("List of statements can't be empty");
    }
    
    if(dto.getTrueFalseAnswers().isEmpty()) {
      throw new InvalidObjectDataException("List of answer can't be empty");
    }

    if(dto.getStatements().size() != dto.getTrueFalseAnswers().size()) {
      throw new InvalidObjectDataException("Statement list and response list sizes can't be different");
    }

    for(String statement : dto.getStatements()) {
      if(statement.trim().isEmpty()) {
        throw new InvalidObjectDataException("No statement in the list can be empty");
      }
    }
  }
}
