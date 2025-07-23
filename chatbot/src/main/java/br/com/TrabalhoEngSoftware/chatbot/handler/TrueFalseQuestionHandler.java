package br.com.TrabalhoEngSoftware.chatbot.handler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.TrabalhoEngSoftware.chatbot.config.Constants;
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
    if(trueFalseQuestion.getStatements().size() != answer.getAnswerList().size()) {
      throw new InvalidObjectDataException("Statement list and user answer list sizes can't be different");
    }

    LocalDateTime tomorrow = LocalDate.now().plusDays(1).atStartOfDay();
    double numberCorrectAnswer = 0;
    List<Boolean> answersList = trueFalseQuestion.getTrueFalseAnswers();
    List<Boolean> userAnswersList = answer.getAnswerList();
    
    for(int i=0; i<answersList.size(); i++) {
      if(answersList.get(i) == userAnswersList.get(i)) {
        numberCorrectAnswer++;
      }
    }
    
    double correctPercentage = numberCorrectAnswer/answersList.size();
    double easeFactor = trueFalseQuestion.getEaseFactor();
    
    if(correctPercentage <= 0.3) {
      if(LocalDateTime.now().plusMinutes(60L).isBefore(tomorrow)){
        trueFalseQuestion.setNextReview(LocalDateTime.now().plusMinutes(60L));
      } else {
        trueFalseQuestion.setNextReview(LocalDate.now().atTime(LocalTime.MAX));
      }
      trueFalseQuestion.setEaseFactor(calculateEaseFactor(easeFactor, Constants.WRONG));
      trueFalseQuestion.setInterval(1);
      return;
    }
    trueFalseQuestion.setNextReview(LocalDateTime.now().plusDays(trueFalseQuestion.getInterval()));
    if(correctPercentage < 0.7) {
      trueFalseQuestion.setEaseFactor(calculateEaseFactor(easeFactor, Constants.HARD));
    } else if(correctPercentage < 1) {
      trueFalseQuestion.setEaseFactor(calculateEaseFactor(easeFactor, Constants.GOOD));
    } else {
      trueFalseQuestion.setEaseFactor(calculateEaseFactor(easeFactor, Constants.EASY));
    }
    trueFalseQuestion.setInterval((int) Math.ceil(trueFalseQuestion.getInterval()*trueFalseQuestion.getEaseFactor()));
  }

  private double calculateEaseFactor(double easeFactor, int answer) {
    double easeFactorTemp = easeFactor - 0.8 + (0.28*answer) - (0.02*Math.pow(answer,2));
    return Math.max(Constants.MIN_EASE_FACTOR, easeFactorTemp);
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
