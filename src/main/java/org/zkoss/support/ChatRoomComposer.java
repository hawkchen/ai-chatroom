package org.zkoss.support;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zul.*;

public class ChatRoomComposer extends SelectorComposer {
    @Wire
    private Label messages;
    @Wire
    private Textbox myMessage;

    @Listen("onOK = textbox")
    public void submit(){
        myMessage.getValue();
    }


}
