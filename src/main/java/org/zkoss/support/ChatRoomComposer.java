package org.zkoss.support;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.*;

public class ChatRoomComposer extends SelectorComposer {
    @Wire(".messageBox")
    protected Div msgBox;
    @Wire
    protected Textbox myMessage;

    @Wire
    private Button send;

    @Wire("checkbox")
    private Checkbox fastCheckbox;

    protected ChatService chatService = new ChatService();

    @Listen("onOK = #myMessage ; onClick = #send")
    public void send(){
        if (myMessage.getValue().isEmpty())
            return;
        if (fastCheckbox.isChecked()){
            submit2();
        }else{
            submit();
        }
    }
    public void submit(){
        appendMyMessage(myMessage.getValue());
        appendAiMessage(myMessage.getValue());
        myMessage.setValue("");
    }

    public void submit2(){
        appendMyMessage(myMessage.getValue());
        myMessage.setValue("");
        Events.echoEvent("onAsk", myMessage, myMessage.getValue());
        Clients.showBusy(myMessage, "wait for reply");
        send.setFocus(true); //move out focus
    }

    @Listen("onAsk = #myMessage")
    public void askAi(Event event){
        appendAiMessage(event.getData().toString());
        Clients.clearBusy(myMessage);
        myMessage.setFocus(true);
    }


    @Listen("onCheck = checkbox")
    public void toggle(){
        send.setIconSclass(send.getIconSclass().equalsIgnoreCase("z-icon-send") ?
                "z-icon-fast-forward" : "z-icon-send");
    }

    protected void appendAiMessage(String myMessage) {
        Div msgDiv = new Div();
        Label aiName = new Label("ChatAI: ");
        aiName.setSclass("ai");
        msgDiv.appendChild(aiName);
        Label msg = new Label(chatService.prompt(myMessage));
        msg.setMultiline(true);
        msgDiv.appendChild(msg);
        msgBox.appendChild(msgDiv);
    }

    protected void appendMyMessage(String myMessage) {
        Div msgDiv = new Div();
        Executions.createComponentsDirectly("<label value=\"Me: \" sclass=\"me\"/>", "zul", msgDiv, null);
        msgDiv.appendChild(new Label(myMessage));
        msgBox.appendChild(msgDiv);
    }


}
