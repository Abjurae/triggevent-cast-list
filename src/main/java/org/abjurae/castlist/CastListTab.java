package org.abjurae.castlist;

import gg.xp.reevent.events.EventContext;
import gg.xp.reevent.events.InitEvent;
import gg.xp.reevent.scan.HandleEvents;
import gg.xp.reevent.scan.ScanMe;
import gg.xp.xivsupport.gui.TitleBorderFullsizePanel;
import gg.xp.xivsupport.gui.extra.PluginTab;

import javax.swing.*;
import java.awt.*;

@ScanMe
public class CastListTab implements PluginTab {

	private JLabel initLabel;
	private volatile boolean initReceived;

	@Override
	public String getTabName() {
		return "Cast List Overlay";
	}

	@Override
	public Component getTabContents() {
		TitleBorderFullsizePanel panel = new TitleBorderFullsizePanel("Cast List");
		panel.add(new JLabel("It Works! (Out of tree version)"));
		initLabel = new JLabel();
		panel.add(initLabel);
		recalc();
		return panel;
	}

	private void recalc() {
		if (initLabel != null) {
			initLabel.setText(initReceived ? "Init Event Received!" : "No Init Event Received Yet...");
		}
	}


	@HandleEvents
	public void handleEvents(EventContext context, InitEvent init) {
		initReceived = true;
		recalc();
	}
}
