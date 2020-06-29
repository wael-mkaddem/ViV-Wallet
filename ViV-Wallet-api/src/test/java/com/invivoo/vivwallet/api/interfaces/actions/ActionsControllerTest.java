package com.invivoo.vivwallet.api.interfaces.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invivoo.vivwallet.api.ViVWalletApiApplication;
import com.invivoo.vivwallet.api.domain.action.Action;
import com.invivoo.vivwallet.api.domain.action.ActionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ViVWalletApiApplication.class)
@WebMvcTest(ActionsController.class)
public class ActionsControllerTest {

    private static final Action ACTION1 = Action.builder().date(LocalDateTime.now()).build();
    private static final Action ACTION2 = Action.builder().date(LocalDateTime.now().minusDays(10)).build();
    private static final Action ACTION3 = Action.builder().date(LocalDateTime.now().minusDays(100)).build();
    private static final List<Action> TEST_ACTIONS = Arrays.asList(ACTION1, ACTION2, ACTION3);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActionRepository actionRepository;

    @Test
    public void given_manager_when_calling_getActionsOrderedByDateDesc_then_return_sorted_actions() throws Exception {
        //given
        Mockito.when(actionRepository.findAllOrderByDate())
               .thenReturn(TEST_ACTIONS);
        String jsonTestActions = MAPPER.writeValueAsString(TEST_ACTIONS);
        //when
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/actions"))
                                                  .andDo(MockMvcResultHandlers.print());
        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                     .andExpect(MockMvcResultMatchers.content().string(jsonTestActions))
                     .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value(TEST_ACTIONS.get(0).getDate()))
                     .andExpect(MockMvcResultMatchers.jsonPath("$[1].date").value(TEST_ACTIONS.get(1).getDate()))
                     .andExpect(MockMvcResultMatchers.jsonPath("$[2].date").value(TEST_ACTIONS.get(2).getDate()));
    }
}