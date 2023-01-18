package org.zkoss.support;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zul.*;

public class ChatRoomComposer extends SelectorComposer {
    @Wire(".messageBox")
    private Vlayout msgBox;
    @Wire
    private Textbox myMessage;

    private ChatService chatService = new ChatService();

    @Listen("onOK = textbox")
    public void submit(){
        msgBox.appendChild(new Label(chatService.prompt(myMessage.getValue())));
        myMessage.setValue("");
    }


}
