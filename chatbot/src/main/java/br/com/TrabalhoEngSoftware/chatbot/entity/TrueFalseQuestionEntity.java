package br.com.TrabalhoEngSoftware.chatbot.entity;

import java.time.LocalDateTime;
import java.util.List;

import br.com.TrabalhoEngSoftwareFramework.framework.entity.FlashcardEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

@Entity
@DiscriminatorValue("TRUE_FALSE_QUESTION")
public class TrueFalseQuestionEntity extends FlashcardEntity {
  @Column(nullable = false)
	private String question;

  @ElementCollection
  @CollectionTable(name = "statements", joinColumns = @JoinColumn(name = "flashcard_id"))
  @Column(nullable = false)
  private List<String> statements;

  @ElementCollection
  @CollectionTable(name = "statements", joinColumns = @JoinColumn(name = "flashcard_id"))
  @Column(nullable = false)
  private List<Boolean> trueFalseAnswers;

  @Column(nullable = false)
	private LocalDateTime nextReview;
	
	// Quantidade de vezes que o card foi revisado com sucesso (sequencialmente).
	// Inicia em zero.
	@Column(nullable = false)
	private int repetition;
	
	// Grau de facilidade do flashcard.
	// Começa com 2.5 e muda conforme o desempenho do usuário
	@Column(nullable = false)
	private double easeFactor;
	
	//Quantidade de dias até a próxima revisão.
	//Começa em 1 e cresce conforme o algoritmo.
	// interval *= easeFactor;
	@Column(nullable = false)
	private int interval;

  public TrueFalseQuestionEntity() {
    super();
    super.setFlashcardType("TRUE_FALSE_QUESTION");
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public List<String> getStatements() {
    return statements;
  }

  public void setStatements(List<String> statements) {
    this.statements = statements;
  }

  public List<Boolean> getTrueFalseAnswers() {
    return trueFalseAnswers;
  }

  public void setTrueFalseAnswers(List<Boolean> trueFalseAnswers) {
    this.trueFalseAnswers = trueFalseAnswers;
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
