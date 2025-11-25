package org.abjurae.castlist;

import gg.xp.reevent.scan.ScanMe;
import gg.xp.xivsupport.gui.WrapLayout;
import gg.xp.xivsupport.gui.extra.TopDownSimplePluginTab;
import gg.xp.xivsupport.persistence.gui.BooleanSettingGui;
import gg.xp.xivsupport.persistence.gui.ColorSettingGui;
import gg.xp.xivsupport.persistence.gui.IntSettingGui;
import gg.xp.xivsupport.persistence.gui.IntSettingSpinner;

import javax.swing.*;
import java.awt.*;

@ScanMe
public class CastListTab extends TopDownSimplePluginTab {
    private final CastListSettings settings;
    private final CastListColorProviderImpl clcp;

	public CastListTab(CastListSettings settings, CastListColorProviderImpl clcp) {
        super("Cast List Overlay", 400);
        this.settings = settings;
        this.clcp = clcp;
    }

    @Override
    public int getSortOrder() {
        return 150;
    }

    @Override
    protected Component[] provideChildren(JPanel outer) {
        JCheckBox reverse = new BooleanSettingGui(settings.getReverse(), "Reverse (Newest Bottom)").getComponent();
        JPanel maxBars = new IntSettingSpinner(settings.getMaxDisplayedBars(), "Max Displayed Bars").getComponent();
        JPanel barWidth = new IntSettingGui(settings.getBarWidth(), "Bar Width").getComponent();
        JPanel barHeight = new IntSettingGui(settings.getBarHeight(), "Bar Height").getComponent();
        JCheckBox showAbilityId = new BooleanSettingGui(settings.getShowAbilityId(), "Show Ability IDs (shortened)").getComponent();

        JLabel colorsLabel = new JLabel("Color Settings");
        JPanel colorsPanel = new JPanel();
        colorsPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 3, 3));
        colorsPanel.add(new ColorSettingGui(clcp.getActiveSetting(), "Base Color", () -> true).getComponent());
        colorsPanel.add(new ColorSettingGui(clcp.getTargetedSetting(), "Targeted (On You) Color", () -> true).getComponent());
        colorsPanel.add(new ColorSettingGui(clcp.getOtherTargetedSetting(), "Targeted (On Others) Color", () -> true).getComponent());
        colorsPanel.add(new ColorSettingGui(clcp.getHiddenActorSetting(), "Hidden Cast Color", () -> true).getComponent());
        colorsPanel.add(new ColorSettingGui(clcp.getFontSetting(), "Font Color", () -> true).getComponent());

        return new Component[]{reverse, maxBars, barWidth, barHeight, showAbilityId, colorsLabel, colorsPanel};
	}
}
