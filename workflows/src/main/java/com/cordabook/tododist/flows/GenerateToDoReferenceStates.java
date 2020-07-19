package com.cordabook.tododist.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.cordabook.tododist.contracts.Command;
import com.cordabook.tododist.states.todo.ToDoStatusRefState;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.contracts.StatePointer;
import net.corda.core.contracts.StaticPointer;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.node.ServiceHub;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;

import java.util.Collections;

@StartableByRPC
public class GenerateToDoReferenceStates extends FlowLogic<Boolean> {
    @Suspendable
    @Override
    public Boolean call() throws FlowException {
        ServiceHub sb = getServiceHub();

        //WithReferencedStatesFlow


        Party notary = sb.getNetworkMapCache().getNotaryIdentities().get(0);

        ToDoStatusRefState refStateOpen = new ToDoStatusRefState(getOurIdentity(), "Open");
        ToDoStatusRefState refStateCompleted = new ToDoStatusRefState(getOurIdentity(), "Complete");

        TransactionBuilder tb = new TransactionBuilder(notary)
                .addOutputState(refStateOpen)
                .addCommand(new Command.GenToDoRefStatesCommand(),getOurIdentity().getOwningKey());
        SignedTransaction stx = sb.signInitialTransaction(tb);
        subFlow(new FinalityFlow(stx, Collections.<FlowSession>emptySet()));

        tb = new TransactionBuilder(notary)
                .addOutputState(refStateCompleted)
                .addCommand(new Command.GenToDoRefStatesCommand(),getOurIdentity().getOwningKey());
        stx = sb.signInitialTransaction(tb);
        subFlow(new FinalityFlow(stx, Collections.<FlowSession>emptySet()));


        return false;
    }
}
