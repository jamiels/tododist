package com.cordabook.tododist.contracts;

import net.corda.core.contracts.CommandData;

public interface Command extends CommandData {
    class CreateToDoCommand implements CommandData {}
    class AssignToDoCommand implements CommandData {}
    class AttachDocToDoCommand implements CommandData {}
    class GenToDoRefStatesCommand implements CommandData {}

}
