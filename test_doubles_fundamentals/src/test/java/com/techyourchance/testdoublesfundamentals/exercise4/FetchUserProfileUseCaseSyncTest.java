package com.techyourchance.testdoublesfundamentals.exercise4;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.exercise4.users.User;
import com.techyourchance.testdoublesfundamentals.exercise4.users.UsersCache;

import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.techyourchance.testdoublesfundamentals.exercise4.FetchUserProfileUseCaseSync.UseCaseResult.FAILURE;
import static com.techyourchance.testdoublesfundamentals.exercise4.FetchUserProfileUseCaseSync.UseCaseResult.NETWORK_ERROR;
import static com.techyourchance.testdoublesfundamentals.exercise4.FetchUserProfileUseCaseSync.UseCaseResult.SUCCESS;
import static com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync.EndpointResultStatus.AUTH_ERROR;
import static com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR;
import static com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync.EndpointResultStatus.SERVER_ERROR;

public class FetchUserProfileUseCaseSyncTest {

    public static final String USER_ID = "124";
    FetchUserProfileUseCaseSync SUT;
    private UserProfileHttpEndpointSyncTestDouble mUserProfileHttpEndpointSync;
    private UsersCacheTestDouble mUsersCache;

    @Before
    public void setUp() throws Exception {
        mUserProfileHttpEndpointSync = new UserProfileHttpEndpointSyncTestDouble();
        mUsersCache = new UsersCacheTestDouble();
        SUT = new FetchUserProfileUseCaseSync(mUserProfileHttpEndpointSync, mUsersCache);
    }

    //success and user Id passed to endpoint

    @Test
    public void getUserProfile_success_userIdpassedtoEndpoint() {
        //Arrange
        mUserProfileHttpEndpointSync.setReturnResponseStatus(UserProfileHttpEndpointSync.EndpointResultStatus.SUCCESS);
        //Act
        SUT.fetchUserProfileSync(USER_ID);
        //Assert
        Assert.assertEquals(USER_ID,mUserProfileHttpEndpointSync.getUserId());
    }

    //success and user cached
    @Test
    public void getUserProfile_success_userDataCached() {
        //Arrange
        mUserProfileHttpEndpointSync.setReturnResponseStatus(UserProfileHttpEndpointSync.EndpointResultStatus.SUCCESS);
        //Act
        SUT.fetchUserProfileSync(USER_ID);
        //Assert
        Assert.assertNotNull(mUsersCache.getUser(USER_ID));
        Assert.assertEquals(USER_ID,mUsersCache.getUser(USER_ID).getUserId());
    }
    //success and success returned
    @Test
    public void getUserProfile_success_successReturned() {
        mUserProfileHttpEndpointSync.setReturnResponseStatus(UserProfileHttpEndpointSync.EndpointResultStatus.SUCCESS);
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        Assert.assertEquals(SUCCESS,result);
    }

    //Network failure and NetworkFailure Returned
    @Test
    public void getUserProfile_networkError_networkErrorReturned() {
        //Arrange
        mUserProfileHttpEndpointSync.mNetworkError = true;
        //Act
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        //Assert
        Assert.assertEquals(NETWORK_ERROR,result);
    }


    //General ERROR and GENERAL ERROR STATUS returned
    @Test
    public void getUserProfile_generalError_FailureReturned() {
        //Arrange
        mUserProfileHttpEndpointSync.setReturnResponseStatus(GENERAL_ERROR);
        //Act
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        //Assert
        Assert.assertEquals(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE,result);
    }
    //AUTH ERROR  and AUTH ERROR RETURNED

    @Test
    public void getUserProfile_AuthError_FailureReturned() {
        //Arrange
        mUserProfileHttpEndpointSync.setReturnResponseStatus(AUTH_ERROR);
        //Act
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        //Assert
        Assert.assertEquals(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE,result);
    }


    //Server ERROR  and SERVER ERROR RETURNED

    @Test
    public void getUserProfile_serverError_failureReturned() {
        //Arrange
        mUserProfileHttpEndpointSync.setReturnResponseStatus(SERVER_ERROR);
        //Act
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        //Assert
        Assert.assertEquals(FAILURE,result);
    }

    //Network failure and user data untouched

    @Test
    public void getUserProfile_networkError_noInteractionWithCache() {
        //Arrange
        mUserProfileHttpEndpointSync.mNetworkError = true;
        //Act
        SUT.fetchUserProfileSync(USER_ID);
        //Assert
        Assert.assertNull(mUsersCache.getUser(USER_ID));
    }

    //General ERROR and user data untouched
    @Test
    public void getUserProfile_generalError_noInteractionWithCache() {
        //Arrange
        mUserProfileHttpEndpointSync.setReturnResponseStatus(GENERAL_ERROR);
        //Act
        SUT.fetchUserProfileSync(USER_ID);
        //Assert
        Assert.assertNull(mUsersCache.getUser(USER_ID));
    }
    //AUTH ERROR  and user data untouched
    @Test
    public void getUserProfile_authError_noInteractionWithCache() {
        //Arrange
        mUserProfileHttpEndpointSync.setReturnResponseStatus(AUTH_ERROR);
        //Act
        SUT.fetchUserProfileSync(USER_ID);
        //Assert
        Assert.assertNull(mUsersCache.getUser(USER_ID));
    }
    //Server ERROR  and user data untouched
    @Test
    public void getUserProfile_serverError_noInteractionWithCache() {
        //Arrange
        mUserProfileHttpEndpointSync.setReturnResponseStatus(SERVER_ERROR);
        //Act
        SUT.fetchUserProfileSync(USER_ID);
        //Assert
        Assert.assertNull(mUsersCache.getUser(USER_ID));
    }


    //helper classes
    private class UserProfileHttpEndpointSyncTestDouble implements UserProfileHttpEndpointSync {

        EndpointResultStatus returnResponseStatus;
        boolean mNetworkError ;
        private String mUserId;

        public void setReturnResponseStatus(EndpointResultStatus returnResponseStatus) {
            this.returnResponseStatus = returnResponseStatus;
        }

        public UserProfileHttpEndpointSyncTestDouble() {
        }

        @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            if(mNetworkError){
                throw new NetworkErrorException();
            }
            switch (returnResponseStatus){
                case AUTH_ERROR:
                case GENERAL_ERROR:
                case SERVER_ERROR:
                    return new EndpointResult(returnResponseStatus,"","","");
                case SUCCESS:
                    mUserId = userId;
                    return new EndpointResult(EndpointResultStatus.SUCCESS,userId,"Adwait","");
                default:
                    return new EndpointResult(GENERAL_ERROR,userId,"","");
            }
        }

        public String getUserId() {
            return mUserId;
        }
    }

    private class UsersCacheTestDouble implements UsersCache{
        User cachedCopy;
        @Override
        public void cacheUser(User user) {
            cachedCopy = user;
        }

        @Nullable
        @Override
        public User getUser(String userId) {
            return cachedCopy;
        }
    }
}