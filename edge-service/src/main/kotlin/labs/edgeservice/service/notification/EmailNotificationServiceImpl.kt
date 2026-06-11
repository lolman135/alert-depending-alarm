package labs.edgeservice.service.notification

import jakarta.mail.internet.MimeMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import labs.edgeservice.usecase.exception.NotificationFailedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

@Component
class EmailNotificationServiceImpl(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.username}") private val fromEmail: String,
    @Value("\${app.mail.to}") private val toEmail: String
) : NotificationService {

    override suspend fun notifyAllClear() = withContext(Dispatchers.IO) {
        try {
            val message: MimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, false, "UTF-8")

            helper.setFrom(fromEmail)
            helper.setTo(toEmail)

            helper.setSubject("[ALL CLEAR] TIME TO WAKE UUUUP")
            helper.setText("Відбій повітряної тривоги, треба вставати :)", false)
            mailSender.send(message)
        } catch (ex: Exception){
            throw NotificationFailedException(ex.message!!)
        }

    }
}