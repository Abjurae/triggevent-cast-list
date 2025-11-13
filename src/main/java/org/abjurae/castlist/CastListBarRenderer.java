package org.abjurae.castlist;

import gg.xp.xivsupport.events.state.combatstate.CastTracker;
import gg.xp.xivsupport.gui.tables.renderers.EmptyRenderer;
import gg.xp.xivsupport.gui.tables.renderers.TimelineBar;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CastListBarRenderer implements TableCellRenderer {
    private final TimelineBar bar = new TimelineBar();
    private final TableCellRenderer fallback = new DefaultTableCellRenderer();
    private final CastListColorProvider colorProvider;

    public CastListBarRenderer(CastListColorProvider colorProvider) {
        this.colorProvider = colorProvider;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof CastListOverlay.CastTrackerListWrapper tracker && tracker.tracker() != null) {
            Component baseLabel = fallback.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, column);
            double percent = tracker.tracker().getEstimatedElapsedDuration().toMillis() / (double) tracker.tracker().getCastDuration().toMillis();

            Color barColor = getBarColor(tracker.tracker());
            bar.setColor1(barColor);
            Color originalBg = baseLabel.getBackground();
            bar.setColor3(new Color(originalBg.getRed(), originalBg.getGreen(), originalBg.getBlue(), 128));
            bar.setBorderColor(originalBg);


            bar.setPercent1(percent);
            bar.setTextColor(colorProvider.getFontColor());

            formatLabel(tracker.tracker());

            return bar;
        }
        return emptyComponent(isSelected, table);

    }

    protected void formatLabel(@NotNull CastTracker item) {
        bar.setLeftTextOptions(item.getCast().getAbility().getName());
        bar.setRightText(String.format("%.1f", item.getEstimatedRemainingDuration().toMillis() / 1000d));
        bar.seticon(null);
    }

    protected Color getBarColor(@NotNull CastTracker item) {
        if (item.getCast().getTarget().isThePlayer()) {
            return colorProvider.getTargetedColor();
        }
        if (item.getCast().getSource().isFake()) {
            return colorProvider.getHiddenActorColor();
        }
        return colorProvider.getActiveColor();
    }

    private final EmptyRenderer empty = new EmptyRenderer();

    private Component emptyComponent(boolean isSelected, JTable table) {
        if (isSelected) {
            empty.setBackground(table.getSelectionBackground());
        }
        else {
            empty.setBackground(null);
        }
        return empty;
    }

}
