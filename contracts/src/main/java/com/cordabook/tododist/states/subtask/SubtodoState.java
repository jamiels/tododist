package com.cordabook.tododist.states.subtask;

import com.cordabook.tododist.states.todo.ToDoState;
import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.LinearPointer;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SubtodoState implements LinearState {
    private final LinearPointer<ToDoState> parentToDo;
    private final String subTaskDescription;
    private final Party addedBy;
    private final UniqueIdentifier linearId;

    public SubtodoState(LinearPointer<ToDoState> parentToDo, String subTaskDescription, Party addedBy, UniqueIdentifier linearId) {
        this.parentToDo = parentToDo;
        this.subTaskDescription = subTaskDescription;
        this.addedBy = addedBy;
        this.linearId = new UniqueIdentifier();
    }


    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return linearId;
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(addedBy);
    }
}
