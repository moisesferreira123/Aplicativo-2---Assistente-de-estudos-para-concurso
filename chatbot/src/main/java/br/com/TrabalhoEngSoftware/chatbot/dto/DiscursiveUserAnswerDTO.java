package br.com.TrabalhoEngSoftware.chatbot.dto;

import br.com.TrabalhoEngSoftwareFramework.framework.dto.UserAnswerDTO;

public class DiscursiveUserAnswerDTO extends UserAnswerDTO {
  
  private String answer;

  public DiscursiveUserAnswerDTO() {
    super();
  }

  public DiscursiveUserAnswerDTO(Long flashcardId, String flashcardType, String answer) {
    super(flashcardId, flashcardType);
    this.answer = answer;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }
}
