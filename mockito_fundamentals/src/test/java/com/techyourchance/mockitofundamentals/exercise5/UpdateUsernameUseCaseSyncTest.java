package com.techyourchance.mockitofundamentals.exercise5;



import com.techyourchance.mockitofundamentals.exercise5.eventbus.EventBusPoster;
import com.techyourchance.mockitofundamentals.exercise5.eventbus.UserDetailsChangedEvent;
import com.techyourchance.mockitofundamentals.exercise5.networking.NetworkErrorException;
import com.techyourchance.mockitofundamentals.exercise5.networking.UpdateUsernameHttpEndpointSync;
import com.techyourchance.mockitofundamentals.exercise5.users.User;
import com.techyourchance.mockitofundamentals.exercise5.users.UsersCache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUsernameUseCaseSyncTest {

    public static final String USERNAME = "adwait";
    public static final String USER_ID = "123";
    @Mock
    UpdateUsernameHttpEndpointSync updateUsernameHttpEndpointSync;

    @Mock
    UsersCache usersCache;

    @Mock
    EventBusPoster eventBusPoster;

    UpdateUsernameUseCaseSync SUT;
    private User cachedUser;
    private Object objectPosted;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        SUT = new UpdateUsernameUseCaseSync(updateUsernameHttpEndpointSync,usersCache,eventBusPoster);
        setUpEventBus();
        setUpCache();
        success();
    }


    @Test
    public void updateUsernameSync_success_successReturned() throws Exception {
        //Arrange
        //Act
        UpdateUsernameUseCaseSync.UseCaseResult result = SUT.updateUsernameSync(USER_ID, USERNAME);
        //Assert
        Assert.assertEquals(UpdateUsernameUseCaseSync.UseCaseResult.SUCCESS,result);
    }


    @Test
    public void updateUsernameSync_authError_failureReturned() throws Exception {
        //Arrange
        failureWithAuthError();
        //Act
        UpdateUsernameUseCaseSync.UseCaseResult result = SUT.updateUsernameSync(USER_ID, USERNAME);
        //Assert
        Assert.assertEquals(UpdateUsernameUseCaseSync.UseCaseResult.FAILURE,result);
    }

    @Test
    public void updateUsernameSync_networkError_networkErrorReturned() throws Exception {
        //Arrange
        failureWithNetworkError();
        //Act
        UpdateUsernameUseCaseSync.UseCaseResult result = SUT.updateUsernameSync(USER_ID, USERNAME);
        //Assert
        Assert.assertEquals(UpdateUsernameUseCaseSync.UseCaseResult.NETWORK_ERROR,result);
    }


    @Test
    public void updateUsernameSync_serverError_failureReturned() throws Exception {
        //Arrange
        failureWithServerError();
        //Act
        UpdateUsernameUseCaseSync.UseCaseResult result = SUT.updateUsernameSync(USER_ID, USERNAME);
        //Assert
        Assert.assertEquals(UpdateUsernameUseCaseSync.UseCaseResult.FAILURE,result);
    }

    @Test
    public void updateUsernameSync_generalError_failureReturned() throws Exception {
        //Arrange
        failureWithGeneralError();
        //Act
        UpdateUsernameUseCaseSync.UseCaseResult result = SUT.updateUsernameSync(USER_ID, USERNAME);
        //Assert
        Assert.assertEquals(UpdateUsernameUseCaseSync.UseCaseResult.FAILURE,result);
    }

    @Test
    public void updateUsernameSync_success_cacheUpdated() {
        //Arrange
        //Act
        SUT.updateUsernameSync(USER_ID,USERNAME);
        //Assert
        Mockito.verify(usersCache,Mockito.times(1)).cacheUser(Mockito.any(User.class));
        Assert.assertEquals(USER_ID,usersCache.getUser(USER_ID).getUserId());
        Assert.assertEquals(USERNAME,usersCache.getUser(USER_ID).getUsername());

    }



    @Test
    public void updateUsernameSync_failureGeneralError_cacheUnchanged() throws Exception {
        //Arrange
        failureWithGeneralError();
        //Act
        SUT.updateUsernameSync(USER_ID,USERNAME);
        //Assert
       Mockito.verifyNoMoreInteractions(usersCache);
    }


    @Test
    public void updateUsernameSync_failureAuthError_cacheUnchanged() throws Exception {
        //Arrange
        failureWithAuthError();
        //Act
        SUT.updateUsernameSync(USER_ID,USERNAME);
        //Assert
        Mockito.verifyNoMoreInteractions(usersCache);
    }


    @Test
    public void updateUsernameSync_failureServerError_cacheUnchanged() throws Exception {
        //Arrange
        failureWithServerError();
        //Act
        SUT.updateUsernameSync(USER_ID,USERNAME);
        //Assert
        Mockito.verifyNoMoreInteractions(usersCache);
    }



    @Test
    public void updateUsernameSync_failureNetworkError_cacheUnchanged() throws Exception {
        //Arrange
        failureWithNetworkError();
        //Act
        SUT.updateUsernameSync(USER_ID,USERNAME);
        //Assert
        Mockito.verifyNoMoreInteractions(usersCache);
    }

    @Test
    public void updateUsernameSync_success_eventPostedToEventBus() {
        //Arrange
        //Act
        SUT.updateUsernameSync(USER_ID,USERNAME);
        //Assert
        Mockito.verify(eventBusPoster,Mockito.times(1)).postEvent(Mockito.any(Object.class));
        Assert.assertEquals(new UserDetailsChangedEvent(new User(USER_ID,USERNAME)),objectPosted);
    }

    @Test
    public void updateUsernameSync_generalError_noInteractionWithEventBus() throws Exception {
        //Arrange
        failureWithGeneralError();
        //Act
        SUT.updateUsernameSync(USER_ID,USERNAME);
        //Assert
        Mockito.verifyNoMoreInteractions(eventBusPoster);
    }

    @Test
    public void updateUsernameSync_authError_noInteractionWithEventBus() throws Exception {
        //Arrange
        failureWithAuthError();
        //Act
        SUT.updateUsernameSync(USER_ID,USERNAME);
        //Assert
        Mockito.verifyNoMoreInteractions(eventBusPoster);
    }

    @Test
    public void updateUsernameSync_networkError_noInteractionWithEventBus() throws Exception {
        //Arrange
        failureWithNetworkError();
        //Act
        SUT.updateUsernameSync(USER_ID,USERNAME);
        //Assert
        Mockito.verifyNoMoreInteractions(eventBusPoster);
    }

    @Test
    public void updateUsernameSync_serverError_noInteractionWithEventBus() throws Exception {
        //Arrange
        failureWithServerError();
        //Act
        SUT.updateUsernameSync(USER_ID,USERNAME);
        //Assert
        Mockito.verifyNoMoreInteractions(eventBusPoster);
    }

    @Test
    public void updateUsernameSync_success_parametersReachEndpoint() throws Exception{
        //Arrange
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        //Act
        SUT.updateUsernameSync(USER_ID, USERNAME);
        //Assert
        Mockito.verify(updateUsernameHttpEndpointSync).updateUsername(captor.capture(),captor.capture());
        Assert.assertEquals(USER_ID,captor.getAllValues().get(0));
        Assert.assertEquals(USERNAME,captor.getAllValues().get(1));
    }

//    region helper methods
    private void setUpEventBus() {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                objectPosted = invocation.getArgument(0);
                return null;
            }
        }).when(eventBusPoster).postEvent(Mockito.any(Object.class));
    }

    private void setUpCache() {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                cachedUser = ((User) invocation.getArgument(0));
                return null;
            }
        }).when(usersCache).cacheUser(Mockito.any(User.class));

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String userId = ((String) invocation.getArgument(0));
                if(cachedUser!=null && cachedUser.getUserId()!=null && cachedUser.getUserId().equals(userId)){
                    return cachedUser;
                }else{
                    return null;
                }

            }
        }).when(usersCache).getUser(Mockito.anyString());
    }

    private void success() throws NetworkErrorException {
    Mockito.doAnswer(new Answer() {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            String userId = ((String) invocation.getArgument(0));
            String userName = ((String) invocation.getArgument(1));
            UpdateUsernameHttpEndpointSync.EndpointResult result = new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.SUCCESS, userId, userName);
            return result;
        }
    }).when(updateUsernameHttpEndpointSync).updateUsername(Mockito.anyString(),Mockito.anyString());
}

    private void failureWithAuthError() throws Exception{
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                UpdateUsernameHttpEndpointSync.EndpointResult result = new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.AUTH_ERROR, ((String) invocation.getArgument(0)), ((String) invocation.getArgument(1)));
                return result;
            }
        }).when(updateUsernameHttpEndpointSync).updateUsername(Mockito.anyString(),Mockito.anyString());
    }

    private void failureWithNetworkError() throws Exception{
        Mockito.doThrow(new NetworkErrorException()).when(updateUsernameHttpEndpointSync).updateUsername(Mockito.anyString(),Mockito.anyString());
    }

    private void failureWithServerError() throws Exception{
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                UpdateUsernameHttpEndpointSync.EndpointResult result = new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.SERVER_ERROR, ((String) invocation.getArgument(0)), ((String) invocation.getArgument(1)));
                return result;
            }
        }).when(updateUsernameHttpEndpointSync).updateUsername(Mockito.anyString(),Mockito.anyString());
    }

    private void failureWithGeneralError() throws Exception{
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                UpdateUsernameHttpEndpointSync.EndpointResult result = new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR, ((String) invocation.getArgument(0)), ((String) invocation.getArgument(1)));
                return result;
            }
        }).when(updateUsernameHttpEndpointSync).updateUsername(Mockito.anyString(),Mockito.anyString());
    }

    @Test
    public void UserObject_invocation_returnsCorrectData() {
        //Arrange
        User user = new User(USER_ID, USERNAME);
        //Act

        //Assert
        Assert.assertEquals(USER_ID,user.getUserId());
        Assert.assertEquals(USERNAME,user.getUsername());
    }

    @Test
    public void endpointResultObject_constructorInvocation_returnsCorrectValues() {
        //Arrange
        UpdateUsernameHttpEndpointSync.EndpointResult result = new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.SUCCESS,USER_ID,USERNAME);        //Act
        //Assert
        Assert.assertEquals(UpdateUsernameHttpEndpointSync.EndpointResultStatus.SUCCESS,result.getStatus());
        Assert.assertEquals(USER_ID,result.getUserId());
        Assert.assertEquals(USERNAME,result.getUsername());
    }

    @Test
    public void userDetailsChangedObject_constructorInvocation_returnsCorrectDetails() {
        //Arrange
        User user = new User(USER_ID, USERNAME);
        UserDetailsChangedEvent event = new UserDetailsChangedEvent(user);
        //Act
        //Assert
        Assert.assertEquals(user,event.getUser());

    }


    //    endregion helper methods

}