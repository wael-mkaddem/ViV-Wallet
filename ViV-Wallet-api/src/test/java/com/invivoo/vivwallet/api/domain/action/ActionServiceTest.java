package com.invivoo.vivwallet.api.domain.action;

import com.invivoo.vivwallet.api.domain.user.User;
import com.invivoo.vivwallet.api.infrastructure.lynx.LynxConnector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActionServiceTest {

    @Mock
    private ActionRepository actionRepository;

    @Mock
    private LynxConnector lynxConnector;

    @Test
    public void should_return_empty_list_when_no_actions_for_the_user() {
        //Given
        User user = User.builder().id((long) 1).build();
        ActionService actionService = new ActionService(actionRepository, lynxConnector);

        //When
        List<Action> actions = actionService.findByAchiever(user.getId());

        //Then
        assertThat(actions).isEmpty();
    }

    @Test
    public void should_return_only_actions_of_user_when_existing_actions_for_the_user() {
        //Given
        User user = User.builder().id((long) 1).build();
        Action action1 = Action.builder().id((long) 1).viv(new BigDecimal(10)).achiever(user).build();
        Action action2 = Action.builder().id((long) 2).viv(new BigDecimal(10)).achiever(user).build();
        List<Action> expectedActions = Arrays.asList(action1, action2);
        when(actionRepository.findByAchiever(user.getId())).thenReturn(expectedActions);
        ActionService actionService = new ActionService(actionRepository, lynxConnector);

        //When
        List<Action> actions = actionService.findByAchiever(user.getId());

        //Then
        assertThat(actions).containsExactlyInAnyOrder(action1, action2);
    }

}
