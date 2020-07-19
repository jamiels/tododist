package com.cordabook.tododist.states.todo;

import co.paralleluniverse.fibers.Suspendable;
import com.cordabook.tododist.states.todo.ToDoState;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.contracts.StateRef;
import net.corda.core.flows.*;
import net.corda.core.node.ServiceHub;

@SchedulableFlow
public class AlarmFlow extends FlowLogic<Void> {

    private StateRef thisRef;
    public AlarmFlow(StateRef stateRef) {
        this.thisRef = stateRef;
        System.out.println("AlarmFlow constructor fired");
    }

    @Override
    public Void call() throws FlowException {
        flowStackSnapshot();
        System.out.println("Alarm fired");
        ServiceHub sb = getServiceHub();
        StateAndRef<ContractState> contractStateStateAndRef = sb.toStateAndRef(thisRef);
        ToDoState toDoState = (ToDoState) contractStateStateAndRef.getState().getData();
        System.out.println(toDoState.getTaskDescription());
        sb.getVaultService().addNoteToTransaction(thisRef.getTxhash(),"Remember to do it!");

        return null;
    }
}

