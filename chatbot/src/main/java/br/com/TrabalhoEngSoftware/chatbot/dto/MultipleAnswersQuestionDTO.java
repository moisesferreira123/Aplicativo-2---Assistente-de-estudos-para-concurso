package br.com.TrabalhoEngSoftware.chatbot.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.TrabalhoEngSoftwareFramework.framework.dto.FlashcardDTO;

public class MultipleAnswersQuestionDTO extends FlashcardDTO {
	
  private String question;
  private List<String> answerOptions;
	private List<Integer> correctAnswersIndex;
	private LocalDateTime nextReview;
	private int repetition;
	private double easeFactor;
	private int interval;

  public MultipleAnswersQuestionDTO() {
    super();
		super.setFlashcardType("MULTIPLE_ANSWER_QUESTION");
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public List<String> getAnswerOptions() {
    return answerOptions;
  }

  public void setAnswerOptions(List<String> answerOptions) {
    this.answerOptions = answerOptions;
  }

  public List<Integer> getCorrectAnswersIndex() {
    return correctAnswersIndex;
  }

  public void setCorrectAnswersIndex(List<Integer> correctAnswersIndex) {
    this.correctAnswersIndex = correctAnswersIndex;
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
