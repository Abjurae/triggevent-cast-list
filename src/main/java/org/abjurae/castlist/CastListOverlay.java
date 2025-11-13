package org.abjurae.castlist;

import gg.xp.reevent.scan.ScanMe;
import gg.xp.xivsupport.events.actlines.events.HasDuration;
import gg.xp.xivsupport.events.state.combatstate.ActiveCastRepository;
import gg.xp.xivsupport.events.state.combatstate.CastTracker;
import gg.xp.xivsupport.gui.overlay.OverlayConfig;
import gg.xp.xivsupport.gui.overlay.RefreshLoop;
import gg.xp.xivsupport.gui.overlay.XivOverlay;
import gg.xp.xivsupport.gui.tables.CustomColumn;
import gg.xp.xivsupport.gui.tables.CustomTableModel;
import gg.xp.xivsupport.persistence.PersistenceProvider;
import gg.xp.xivsupport.persistence.settings.BooleanSetting;
import gg.xp.xivsupport.persistence.settings.IntSetting;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;

@ScanMe
public class CastListOverlay extends XivOverlay {
    private final ActiveCastRepository acr;
    private volatile List<CastTrackerListWrapper> current = Collections.emptyList();
    private final IntSetting numberOfRows;
    private final IntSetting barWidth;
    private final IntSetting barHeight;
    private final BooleanSetting reverse;
    private final CustomTableModel<CastTrackerListWrapper> tableModel;
    private final JTable table;

    public CastListOverlay(OverlayConfig oc, PersistenceProvider persistence, ActiveCastRepository acr, CastListSettings settings, CastListColorProvider clcp) {
        super("Cast List", "overlays.cast-list", oc, persistence);
        this.acr = acr;
        this.numberOfRows = settings.getMaxDisplayedBars();
        numberOfRows.addListener(this::repackSize);
        tableModel = CustomTableModel.builder(() -> current)
                .addColumn(new CustomColumn<>("Bar", Function.identity(),
                        c -> {
                            c.setCellRenderer(new CastListBarRenderer(clcp));
                        }))
                .build();
        table = new JTable(tableModel);
        table.setOpaque(false);
        tableModel.configureColumns(table);
        table.setCellSelectionEnabled(false);
        barWidth = settings.getBarWidth();
        barWidth.addListener(this::repackSize);
        barHeight = settings.getBarHeight();
        barHeight.addListener(this::repackSize);
        table.setRowHeight(barHeight.get());
        reverse = settings.getReverse();
        getPanel().add(table);
        RefreshLoop<CastListOverlay> refresher = new RefreshLoop<>("CastListOverlay", this, CastListOverlay::refresh, dt -> dt.calculateScaledFrameTime(50));
        refresher.start();
    }

    @Override
    protected void repackSize() {
        table.setRowHeight(barHeight.get());
        table.setPreferredSize(new Dimension(barWidth.get(), table.getRowHeight() * numberOfRows.get()));
        super.repackSize();
    }


    private void refresh() {
        List<CastTracker> activeCasts = acr.getAll();
        Set<String> uniqueAbilities = new HashSet<>();
        List<CastTrackerListWrapper> filteredCasts = activeCasts.stream()
                .filter(cast -> !cast.getCast().getSource().isPc() && !cast.wouldBeExpired() && uniqueAbilities.add(cast.getCast().getAbility().getId() + "|" + cast.getEstimatedRemainingDuration().toMillis()))
                .sorted(Comparator.comparing(HasDuration::getEstimatedRemainingDuration))
                .limit(numberOfRows.get())
                .map(CastTrackerListWrapper::new)
                .toList();
        if (reverse.get()) {
            List<CastTrackerListWrapper> reversedList = new ArrayList<>(numberOfRows.get());
            for (int i = 0; i < numberOfRows.get(); i++) {
                if (numberOfRows.get() - filteredCasts.size() > i) {
                    reversedList.add(new CastTrackerListWrapper(null));
                } else {
                    reversedList.add(filteredCasts.get(numberOfRows.get() - i - 1));
                }
            }
            current = reversedList;
        } else {
            current = filteredCasts;
        }
        tableModel.fullRefresh();
    }

    public record CastTrackerListWrapper(@Nullable CastTracker tracker) {
    }
}
