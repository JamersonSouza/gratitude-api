package tech.jamersondev.gratitude.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.jamersondev.gratitude.core.controller.CardController;
import tech.jamersondev.gratitude.core.enums.CardTypeEnum;
import tech.jamersondev.gratitude.core.model.Card;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.service.CardServiceImpl;
import tech.jamersondev.gratitude.payload.form.CardForm;
import tech.jamersondev.gratitude.payload.form.CardPageForm;
import tech.jamersondev.gratitude.payload.form.CreateCardForm;
import tech.jamersondev.gratitude.payload.form.UpdateCardForm;
import tech.jamersondev.gratitude.payload.form.UserForm;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {
    @Mock
    private CardServiceImpl cardService;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private CardController cardController;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
    }

    @Test
    @DisplayName("Test Card Controller - Create card")
    void testCardController_WhenCreateCard_thenReturnSaveCard() throws Exception {
        CreateCardForm form = new CreateCardForm("new card", "#fff", CardTypeEnum.DREAM, UUID.randomUUID().toString());

        User user = new User();
        user.setIdentifier(UUID.fromString(form.userId()));
        Card card = new Card(form.cardType(), form.color(), form.text(), user);

        when(cardService.save(form)).thenReturn(card);

        mockMvc.perform(post("/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(form)))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Test Card Controller list Cards")
    void testListCards_WhenCalledWithValidIdentifier_ShouldReturnPage() throws Exception {
        String userIdentifier = UUID.randomUUID().toString();
        int page = 0;
        int size = 10;

        UserForm userForm = new UserForm(UUID.randomUUID().toString(), "james");

        CardPageForm card1 = new CardPageForm("card 1", "#fff", false, userForm, new Date(), UUID.randomUUID().toString(), new Date());
        CardPageForm card2 = new CardPageForm("card 2", "#000",true, userForm, new Date(), UUID.randomUUID().toString(), new Date());

        List<CardPageForm> content = List.of(card1, card2);
        Page<CardPageForm> pageResult = new PageImpl<>(content, PageRequest.of(page, size), content.size());

        // Mock do service
        when(cardService.list(eq(userIdentifier), any(Pageable.class)))
                .thenReturn(pageResult);

        mockMvc.perform(get("/card/"+userIdentifier)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].text").value("card 1"))
                .andExpect(jsonPath("$.content[1].text").value("card 2"))
                .andExpect(jsonPath("$.size").value(size))
                .andExpect(jsonPath("$.number").value(page))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    @DisplayName("Test Card Controller - Delete card")
    void testCardController_WhenDeleteCard_thenReturnSaveCard() throws Exception {
        UUID cardId = UUID.randomUUID();
        // Mock pro método void de deleção
        doNothing().when(cardService).delete(cardId);

        mockMvc.perform(delete("/card/{identifier}", cardId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Test Card Controller - Update card")
    void testCardController_WhenUpdateCard_thenReturnUpdatedCard() throws Exception {
        UUID cardId = UUID.randomUUID();

        UpdateCardForm updateForm = new UpdateCardForm("texto apenas de teste", "#FFF", CardTypeEnum.DREAM);

        Card cardUpdate = mock(Card.class);
        when(cardUpdate.getIdentifier()).thenReturn(cardId);

        when(cardService.update(updateForm, cardId)).thenReturn(cardUpdate);

        mockMvc.perform(put("/card/{identifier}", cardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateForm)))
                .andExpect(status().isOk());
    }
}
