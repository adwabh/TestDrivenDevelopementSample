package com.techyourchance.mockitofundamentals.exercise5.eventbus;

import com.techyourchance.mockitofundamentals.exercise5.users.User;

import java.util.Objects;

public class UserDetailsChangedEvent {

    private final User mUser;

    public UserDetailsChangedEvent(User user) {
        mUser = user;
    }

    public User getUser() {
        return mUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsChangedEvent that = (UserDetailsChangedEvent) o;
        return Objects.equals(mUser, that.mUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUser);
    }
}
