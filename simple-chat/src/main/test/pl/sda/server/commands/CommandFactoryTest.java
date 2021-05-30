package pl.sda.server.commands;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.sda.client.ChatClient;
import pl.sda.client.ClientSocket;
import pl.sda.server.ChatServer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    ChatServer server = new ChatServer(5556, 20);
    ChatClient client = new ChatClient(new ClientSocket("127.0.0.1", 5556).getClient(), server);

    CommandFactoryTest() throws IOException {
    }

    @Test
    void shouldCreateLoginCommand() {
        //given
        String message = "LOGIN user password";

        //when
        Command command = CommandFactory.createCommand(message, client, server);

        //then
        assertEquals(command.getClass(), LoginCommand.class);
    }

    @Test
    void shouldCreateLogoutCommand() {
        //given
        String message = "LOGOUT ";

        //when
        Command command = CommandFactory.createCommand(message, client, server);

        //then
        assertEquals(command.getClass(), LogoutCommand.class);
    }

    @Test
    void shouldCreateRegisterCommand() {
        //given
        String message = "REGISTER user password";

        //when
        Command command = CommandFactory.createCommand(message, client, server);

        //then
        assertEquals(command.getClass(), RegisterCommand.class);
    }

    @Test
    void shouldCreateSendDirectlyCommand() {
        //given
        String message = "SENDTO user message";

        //when
        Command command = CommandFactory.createCommand(message, client, server);

        //then
        assertEquals(command.getClass(), SendDirectlyCommand.class);
    }

    @Test
    void shouldCreateSendToAllCommand() {
        //given
        String message = "SENDALL message";

        //when
        Command command = CommandFactory.createCommand(message, client, server);

        //then
        assertEquals(command.getClass(), SendToAllCommand.class);
    }

    @Test
    void shouldCreateUnknownCommandWhenNoCommandIsUsed() {
        //given
        String message = "user message";

        //when
        Command command = CommandFactory.createCommand(message, client, server);

        //then
        assertEquals(command.getClass(), UnknownCommand.class);
    }

    @Test
    void shouldCreateUnknownCommandWhenThereIsNoSpaceAfterCommand() {
        //given
        String message = "SEDNTOuser message";

        //when
        Command command = CommandFactory.createCommand(message, client, server);

        //then
        assertEquals(command.getClass(), UnknownCommand.class);
    }
}