package dreamdiary.noc.quiz.domain.model

data class ChoiceGroup(
    val values: Collection<Choice>
) {
    init {
        validateChoiceCount()
    }

    internal fun findChoiceByPublicId(publicId: ChoicePublicId): Choice? {
        return values.firstOrNull { it.id.publicId == publicId }
    }

    internal fun containsByChoicePublicId(choicePublicId: ChoicePublicId): Boolean {
        return values.any { it.id.publicId == choicePublicId }
    }

    private fun validateChoiceCount() {
        if (values.isEmpty() || 5 < values.size) throw IllegalArgumentException()
    }
}
