package labs.apistarter.usecase

interface UseCase<I, O> {
    fun execute(inboundCommand: I): O
}