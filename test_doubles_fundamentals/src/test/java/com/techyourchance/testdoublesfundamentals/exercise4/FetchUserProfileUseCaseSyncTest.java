package com.techyourchance.testdoublesfundamentals.exercise4;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.exercise4.users.User;
import com.techyourchance.testdoublesfundamentals.exercise4.users.UsersCache;

import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync.EndpointResultStatus.AUTH_ERROR;
import static com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR;
import static com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync.EndpointResultStatus.SUCCESS;

public class FetchUserProfileUseCaseSyncTest {

    FetchUserProfileUseCaseSync SUT;
    private MockUserProfileHttpEndpointSync mUserProfileHttpEndpointSync;
    private MockUsersCache mUsersCache;

    @Before
    public void setUp() throws Exception {
        SUT = new FetchUserProfileUseCaseSync(mUserProfileHttpEndpointSync, mUsersCache);
    }

    //success and user cached

    @Test
    public void getUserProfile_success_userdataReceived() {

        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync("124");
        Assert.assertEquals(result,SUCCESS);
    }

    //Network failure and NetworkFailure Returned
    //General ERROR and GENERAL ERROR STATUS returned
    //AUTH ERROR  and AUTH ERROR RETURNED
    //Server ERROR  and SERVER ERROR RETURNED

    //Network failure and user data untouched
    //General ERROR and user data untouched
    //AUTH ERROR  and user data untouched
    //Server ERROR  and user data untouched


    //helper classes
    private class MockUserProfileHttpEndpointSync implements UserProfileHttpEndpointSync {

        EndpointResultStatus returnResponseStatus;
        boolean mNetworkError ;

        public MockUserProfileHttpEndpointSync(EndpointResultStatus status) {
            this.returnResponseStatus = status;
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
                    new EndpointResult(returnResponseStatus,"","","");
                case SUCCESS:
                    return new EndpointResult(EndpointResultStatus.SUCCESS,userId,"Adwait","");
                default:
                    new EndpointResult(GENERAL_ERROR,userId,"","");
            }
         return new EndpointResult(GENERAL_ERROR,"","","");
        }
    }

    private class MockUsersCache implements UsersCache{
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