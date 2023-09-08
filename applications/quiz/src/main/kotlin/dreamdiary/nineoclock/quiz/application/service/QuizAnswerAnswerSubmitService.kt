package dreamdiary.nineoclock.quiz.application.service

import dreamdiary.nineoclock.quiz.application.usecase.QuizAnswerSubmitCommand
import dreamdiary.nineoclock.quiz.application.usecase.QuizAnswerSubmitUseCase
import dreamdiary.nineoclock.quiz.domain.outbound.QuizAnswerSubmitOutPort
import dreamdiary.nineoclock.quiz.domain.outbound.QuizOutPort
import dreamdiary.nineoclock.quiz.domain.service.QuizActionAuthorizer
import dreamdiary.nineoclock.shard.identifier.ChoicePublicId
import dreamdiary.nineoclock.shard.identifier.QuizPublicId
import dreamdiary.nineoclock.shard.inline.isNotEmptyToThrow
import jakarta.validation.Validator
import org.springframework.stereotype.Service

@Service
internal class QuizAnswerAnswerSubmitService(
    private val validator: Validator,
    private val authorizer: QuizActionAuthorizer,
    private val quizOutPort: QuizOutPort,
    private val quizAnswerSubmitOutPort: QuizAnswerSubmitOutPort
) : QuizAnswerSubmitUseCase {
    override fun submitQuizAnswer(command: QuizAnswerSubmitCommand) {
        validator.validate(command).isNotEmptyToThrow { RuntimeException() }
        if (!authorizer.authorizeQuizSubmitAction(command.submitterSecureId)) throw RuntimeException()

        val quiz = quizOutPort.findByPublicId(QuizPublicId(command.quizPublicId)) ?: throw RuntimeException()
        val submit = quiz.validateAndToSubmit(command.submitterSecureId, ChoicePublicId(command.choicePublicId))

        quizAnswerSubmitOutPort.save(submit)
    }
}
