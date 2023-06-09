package dreamdiary.quiz.api;

import dreamdiary.credential.MockMemberCredentialResolver;
import dreamdiary.quiz.app.QuizSubmitRequest;
import dreamdiary.quiz.app.QuizSubmitUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class QuizSubmitApiTest {
    @InjectMocks
    private QuizSubmitApi quizSubmitApi;
    @Mock
    private QuizSubmitUseCase mockQuizSubmitUseCase;
    private MockMvc mockMvc;
    @Captor
    private ArgumentCaptor<String> memberIdCaptor;
    @Captor
    private ArgumentCaptor<String> quizIdCaptor;
    @Captor
    private ArgumentCaptor<QuizSubmitRequest> requestCaptor;
    @Spy
    private MockMemberCredentialResolver mockMemberCredentialResolver;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(quizSubmitApi)
                .setCustomArgumentResolvers(mockMemberCredentialResolver)
                .build();
    }

    @Test
    void submitQuiz_status_is_ok() throws Exception {
        mockMvc.perform(post("/quizzes/{quizPublicId}/submit", "quizPublicKey")
                        .header("X-Member-Public-Id", "memberPublicId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void submitQuiz_passes_data_to_useCase() throws Exception {
        final String givenMemberPublicId = "memberPublicId";
        final String givenQuizPublicId = "quizPublicKey";
        final String givenChoiceId = UUID.randomUUID().toString();

        mockMvc.perform(post("/quizzes/{quizPublicId}/submit", "quizPublicKey")
                .header("X-Member-Public-Id", givenMemberPublicId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "choicePublicId": "%s"
                        }
                        """.formatted(givenChoiceId)));

        verify(mockQuizSubmitUseCase, times(1)).submitQuiz(memberIdCaptor.capture(), quizIdCaptor.capture(), requestCaptor.capture());
        assertThat(memberIdCaptor.getValue()).isEqualTo(givenMemberPublicId);
        assertThat(quizIdCaptor.getValue()).isEqualTo(givenQuizPublicId);
    }
}
