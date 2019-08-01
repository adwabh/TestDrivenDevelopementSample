package com.techyourchance.mockitofundamentals.exercise5;


import com.techyourchance.mockitofundamentals.exercise5.eventbus.EventBusPoster;
import com.techyourchance.mockitofundamentals.exercise5.networking.UpdateUsernameHttpEndpointSync;
import com.techyourchance.mockitofundamentals.exercise5.users.UsersCache;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUsernameUseCaseSyncTest {

    @Mock
    UpdateUsernameHttpEndpointSync updateUsernameHttpEndpointSync;

    @Mock
    UsersCache usersCache;

    @Mock
    EventBusPoster eventBusPoster;

    UpdateUsernameUseCaseSync usernameUseCaseSync;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        usernameUseCaseSync = new UpdateUsernameUseCaseSync(updateUsernameHttpEndpointSync,usersCache,eventBusPoster);
    }
    //
}