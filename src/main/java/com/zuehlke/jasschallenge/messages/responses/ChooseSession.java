package com.zuehlke.jasschallenge.messages.responses;

import com.zuehlke.jasschallenge.messages.type.ChooseSessionData;
import com.zuehlke.jasschallenge.messages.type.SessionChoice;
import com.zuehlke.jasschallenge.messages.type.SessionType;

public class ChooseSession implements Response {
    private final ChooseSessionData data;

    public ChooseSession(SessionChoice sessionChoice) {
        this(sessionChoice, "Java Client Session", SessionType.TOURNAMENT);
    }

    public ChooseSession(SessionChoice sessionChoice, String sessionName, SessionType tournament) {
        data = new ChooseSessionData(sessionChoice, sessionName, tournament);
    }

    public ChooseSessionData getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChooseSession that = (ChooseSession) o;

        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
