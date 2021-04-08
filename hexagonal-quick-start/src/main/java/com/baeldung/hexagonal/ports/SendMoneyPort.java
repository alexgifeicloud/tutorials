package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.ports.commands.SendMoneyCommand;

public interface SendMoneyPort {

    boolean sendMoney(SendMoneyCommand sendMoneyCommand);

}
