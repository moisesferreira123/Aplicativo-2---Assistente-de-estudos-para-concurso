package br.com.TrabalhoEngSoftware.chatbot.dto;

import br.com.TrabalhoEngSoftwareFramework.framework.dto.UserAnswerDTO;

public class MultipleChoiceUserAnswerDTO extends UserAnswerDTO {
  
  private int answer;

  public MultipleChoiceUserAnswerDTO() {
    super();
  }

  public MultipleChoiceUserAnswerDTO(Long flashcardId, String flashcardType, int answer) {
    super(flashcardId, flashcardType);
    this.answer = answer;
  }

  public int getAnswer() {
    return answer;
  }

  public void setAnswer(int answer) {
    this.answer = answer;
  }
}
