package org.job.sbjobex.service;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmailServiceTest {

    private EmailService emailService;
    private JavaMailSender mailSender;

    @BeforeEach
    void setUp() {
        mailSender = Mockito.mock(JavaMailSender.class);
        emailService = new EmailService(mailSender);
    }


    @Test
    void testSendEmail_Success() throws Exception {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act & Assert
        assertDoesNotThrow(() -> emailService.sendEmail("test@example.com", "Test Subject", "Test Body"));

        // Verify
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void testSendEmail_Failure() throws Exception {
        // Arrange
        when(mailSender.createMimeMessage()).thenThrow(new RuntimeException("Mail server error"));

        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> emailService.sendEmail("test@example.com", "Test Subject", "Test Body"));

        // Verify
        verify(mailSender, times(1)).createMimeMessage();
    }
}