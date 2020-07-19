package com.cordabook.tododist.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.transactions.SignedTransaction;
import org.jetbrains.annotations.NotNull;


@InitiatedBy(MoveTokenInitiator.class)
public class MoveTokenResponder extends FlowLogic<SignedTransaction> {
    private FlowSession counterpartySession;

    public MoveTokenResponder(FlowSession counterpartySession) {
        this.counterpartySession = counterpartySession;
    }
    /*
        flow start CreateToDoFlow task: "Wuzzup"

        flow start AssignTaskFlow linearId: "c2f5286b-e45f-4f1f-be92-c4e9d60f759d", assignedTo: PartyB
     */
    @Suspendable
    @Override
    public SignedTransaction call() throws FlowException {
        System.out.println("responder called");
        final SignTransactionFlow signTransactionFlow = new SignTransactionFlow(counterpartySession) {
            @Override
            protected void checkTransaction(@NotNull SignedTransaction stx) throws FlowException { // when is this called? doesnt seem to be called
                System.out.println("check!!");
            }
        };
        SignedTransaction stx = subFlow(signTransactionFlow);
        return subFlow(new ReceiveFinalityFlow(counterpartySession, stx.getId()));
    }

}