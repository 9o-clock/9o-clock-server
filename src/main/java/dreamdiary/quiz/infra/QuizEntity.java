package dreamdiary.quiz.infra;

import dreamdiary.quiz.domain.model.Choice;
import dreamdiary.quiz.domain.model.Choices;
import dreamdiary.quiz.domain.model.Quiz;
import dreamdiary.quiz.domain.model.QuizContent;
import dreamdiary.quiz.domain.model.QuizPublicId;
import dreamdiary.quiz.domain.model.QuizTitle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "QUIZ", indexes = {
        @Index(name = "IDX_QUIZ_PUBLIC_ID", columnList = "public_id")
})
@Entity
@Getter
class QuizEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = QuizPublicIdConverter.class)
    @Column(name = "public_id", nullable = false, unique = true)
    private QuizPublicId publicId;
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "release_at", nullable = false)
    private LocalDateTime releaseAt;
    @Column(name = "answer_release_at", nullable = false)
    private LocalDateTime answerReleaseAt;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quiz_id")
    private List<ChoiceEntity> choices;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static QuizEntity mapped(final Quiz quiz) {
        final List<ChoiceEntity> choiceEntities = quiz.getChoices().values().stream()
                .map(ChoiceEntity::mapped)
                .toList();
        return builder()
                .publicId(quiz.getQuizPublicId())
                .title(quiz.getTitle().value())
                .content(quiz.getContent().value())
                .choices(choiceEntities)
                .releaseAt(quiz.getReleaseAt())
                .answerReleaseAt(quiz.getAnswerReleaseAt())
                .build();
    }

    public Quiz toQuiz() {
        final List<Choice> choiceList = choices.stream().map(e -> new Choice(e.getPublicId(), e.getText())).toList();
        return Quiz.builder()
                .quizPublicId(this.publicId)
                .title(new QuizTitle(this.title))
                .content(new QuizContent(this.content))
                .choices(new Choices(choiceList))
                .releaseAt(this.releaseAt)
                .answerReleaseAt(this.answerReleaseAt)
                .build();
    }
}

