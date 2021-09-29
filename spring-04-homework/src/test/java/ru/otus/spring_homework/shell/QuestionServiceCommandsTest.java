package ru.otus.spring_homework.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring_homework.service.QuestionService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тест команд shell ")
@SpringBootTest
class QuestionServiceCommandsTest {

    @MockBean
    private QuestionService questionService;

    @Autowired
    private Shell shell;

    private static final String GREETING_PATTERN = "Welcome, %s";
    private static final String DEFAULT_LOGIN = "guest";
    private static final String CUSTOM_LOGIN = "John";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_START = "start";
    private static final String COMMAND_START_EXPECTED_RESULT = "The test is over. Enter \"exit\" to finish or \"start\" to pass the test again";
    private static final String COMMAND_LOGIN_PATTERN = "%s %s";

    @DisplayName("should return greeting for all forms of login command")
    @Test
    void shouldReturnExpectedGreetingAfterLoginCommandEvaluated() {
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_LOGIN));
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, CUSTOM_LOGIN));
    }

    @DisplayName("should return CommandNotCurrentlyAvailable if user start test without login")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserDoesNotLoginAfterTestCommandEvaluated() {
        Object res =  shell.evaluate(() -> COMMAND_START);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("should return result of test command and call performTest if there was login before")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnExpectedMessageAndFirePerformTestMethodAfterTestCommandEvaluated() {
        shell.evaluate(() -> COMMAND_LOGIN);
        String res = (String) shell.evaluate(() -> COMMAND_START);
        assertThat(res).isEqualTo(COMMAND_START_EXPECTED_RESULT);
        verify(questionService, times(1)).performTest();
    }
}