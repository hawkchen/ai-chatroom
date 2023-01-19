import org.junit.Test;
import org.zkoss.support.ChatService;

public class ChatServiceTest {

    @Test
    public void basic(){
        System.out.println(new ChatService().prompt("hello"));
    }
}
