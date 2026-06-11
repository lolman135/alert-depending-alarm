package labs.edgeservice.usecase

interface UseCase<I, O> {
    fun execute(inboundCommand: I): O
}