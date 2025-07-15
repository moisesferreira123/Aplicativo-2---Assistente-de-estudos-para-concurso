package br.com.TrabalhoEngSoftware.chatbot.dto;

import br.com.TrabalhoEngSoftwareFramework.framework.dto.UserAnswerDTO;

public class RightWrongUserAnswerDTO extends UserAnswerDTO {
  
  private Boolean answer;

  public RightWrongUserAnswerDTO() {
    super();
  }

  public RightWrongUserAnswerDTO(Long flashcardId, String flashcardType, Boolean answer) {
    super(flashcardId, flashcardType);
    this.answer = answer;
  }

  public Boolean getAnswer() {
    return answer;
  }

  public void setAnswer(Boolean answer) {
    this.answer = answer;
  }
}
