package br.com.TrabalhoEngSoftware.chatbot.dto;

import java.time.LocalDateTime;

import br.com.TrabalhoEngSoftwareFramework.framework.dto.FlashcardDTO;

public class RightWrongQuestionDTO extends FlashcardDTO {
  
  private String question;
  private boolean isRight;
  private LocalDateTime nextReview;
	private int repetition;
	private double easeFactor;
	private int interval;

  public RightWrongQuestionDTO() {
    super();
		super.setFlashcardType("RIGHT_WRONG_QUESTION");
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public boolean isRight() {
    return isRight;
  }

  public void setRight(boolean isRight) {
    this.isRight = isRight;
  }

  public LocalDateTime getNextReview() {
    return nextReview;
  }

  public void setNextReview(LocalDateTime nextReview) {
    this.nextReview = nextReview;
  }

  public int getRepetition() {
    return repetition;
  }

  public void setRepetition(int repetition) {
    this.repetition = repetition;
  }

  public double getEaseFactor() {
    return easeFactor;
  }

  public void setEaseFactor(double easeFactor) {
    this.easeFactor = easeFactor;
  }

  public int getInterval() {
    return interval;
  }

  public void setInterval(int interval) {
    this.interval = interval;
  }
}
