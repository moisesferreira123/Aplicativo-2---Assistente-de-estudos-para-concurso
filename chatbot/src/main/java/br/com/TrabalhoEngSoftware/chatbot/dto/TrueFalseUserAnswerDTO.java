package br.com.TrabalhoEngSoftware.chatbot.dto;

import java.util.List;

import br.com.TrabalhoEngSoftwareFramework.framework.dto.UserAnswerDTO;

public class TrueFalseUserAnswerDTO extends UserAnswerDTO {
  
  private List<Boolean> answerList;

  public TrueFalseUserAnswerDTO() {
    super();
  }

  public TrueFalseUserAnswerDTO(Long flashcardId, String flashcardType, List<Boolean> answerList) {
    super(flashcardId, flashcardType);
    this.answerList = answerList;
  }

  public List<Boolean> getAnswerList() {
    return answerList;
  }

  public void setAnswerList(List<Boolean> answerList) {
    this.answerList = answerList;
  }
}
