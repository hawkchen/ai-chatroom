import org.junit.Test;
import org.zkoss.support.ChatService;

public class ChatServiceTest {

    @Test
    public void basic(){
        String reply = new ChatService().prompt("How to learn ZK framework?");
        System.out.println(reply);
    }
}
