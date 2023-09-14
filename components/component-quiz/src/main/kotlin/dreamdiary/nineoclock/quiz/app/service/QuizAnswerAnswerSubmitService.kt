package dreamdiary.nineoclock.quiz.app.service

import dreamdiary.nineoclock.quiz.app.usecase.QuizAnswerSubmitCommand
import dreamdiary.nineoclock.quiz.app.usecase.QuizAnswerSubmitUseCase
import dreamdiary.nineoclock.quiz.domain.QuizActionAuthorizer
import dreamdiary.nineoclock.quiz.domain.QuizRepository
import dreamdiary.nineoclock.shard.identifier.ChoicePublicId
import dreamdiary.nineoclock.shard.identifier.QuizPublicId
import jakarta.validation.Validator
import org.springframework.stereotype.Service

@Service
internal class QuizAnswerAnswerSubmitService(
    private val validator: Validator,
    private val authorizer: QuizActionAuthorizer,
    private val quizRepository: QuizRepository,
//    private val quizAnswerSubmitRepository: QuizAnswerSubmitRepository
) : QuizAnswerSubmitUseCase {
    override fun submitQuizAnswer(command: QuizAnswerSubmitCommand) {
        if (validator.validate(command).isNotEmpty()) throw RuntimeException()
        if (!authorizer.authorizeQuizSubmitAction(command.submitterSecureId)) throw RuntimeException()

        val quiz = quizRepository.findByPublicId(QuizPublicId(command.quizPublicId)) ?: throw RuntimeException()
        val submit = quiz.validateAndToSubmit(command.submitterSecureId, ChoicePublicId(command.choicePublicId))

//        quizAnswerSubmitRepository.save(submit)
    }
}
