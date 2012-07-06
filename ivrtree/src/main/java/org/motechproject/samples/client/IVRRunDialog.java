package org.motechproject.samples.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class IVRRunDialog extends DialogBox {
    TextArea output = new TextArea();

    public IVRRunDialog() {
        setText("Play IVR Flow");
        int c = 1;
        final VerticalPanel container = new VerticalPanel();
        add(container);
        container.add(output);
        final Grid grid = new Grid(6, 3);
        grid.setStyleName("DialPad");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid.setWidget(i, j, new Button("" + c++));
            }
        }
        grid.setWidget(3, 0, new Button("*"));
        grid.setWidget(3, 1, new Button("0"));
        grid.setWidget(3, 2, new Button("#"));
        final Button dialButton = new Button("<img src=\"/images/call.png\"></img>");
        final Button hangupButton = new Button("<img src=\"/images/hangup_call.png\"></img>");
        dialButton.setStyleName("DialButton");
        grid.setWidget(5, 2, dialButton);
        hangupButton.setStyleName("HangupButton");
        grid.setWidget(5, 0, hangupButton);
        container.add(grid);

        hangupButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
               IVRRunDialog.this.hide();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        center();
    }
}
