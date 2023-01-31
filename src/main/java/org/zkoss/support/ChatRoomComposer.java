package org.zkoss.support;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zul.*;

public class ChatRoomComposer extends SelectorComposer {
    @Wire(".messageBox")
    private Div msgBox;
    @Wire
    private Textbox myMessage;

    private ChatService chatService = new ChatService();

    @Listen("onOK = textbox ; onClick = #send")
    public void submit(){
        appendMyMessage();
        appendAiMessage();
        myMessage.setValue("");
    }

    private void appendAiMessage() {
        Div msgDiv = new Div();
        Label aiName = new Label("ChatAI: ");
        aiName.setSclass("ai");
        msgDiv.appendChild(aiName);
        msgDiv.appendChild(new Label(chatService.prompt(myMessage.getValue())));
        msgBox.appendChild(msgDiv);
    }

    private void appendMyMessage(){
        Div msgDiv = new Div();
        Executions.createComponentsDirectly("<label value=\"Me: \" sclass=\"me\"/>", "zul", msgDiv, null);
        msgDiv.appendChild(new Label(myMessage.getValue()));
        msgBox.appendChild(msgDiv);
    }


}
