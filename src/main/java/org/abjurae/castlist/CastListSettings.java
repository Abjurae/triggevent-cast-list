package org.abjurae.castlist;

import gg.xp.reevent.scan.ScanMe;
import gg.xp.xivsupport.persistence.PersistenceProvider;
import gg.xp.xivsupport.persistence.settings.BooleanSetting;
import gg.xp.xivsupport.persistence.settings.IntSetting;

@ScanMe
public final class CastListSettings {
    private final IntSetting maxDisplayedBars;
    private final IntSetting barWidth;
    private final IntSetting barHeight;
    private final BooleanSetting reverse;
    private final BooleanSetting showAbilityId;

    public CastListSettings(PersistenceProvider persistence) {
        maxDisplayedBars = new IntSetting(persistence, "cast-list.max-displayed", 5, 1, 32);
        barWidth = new IntSetting(persistence, "cast-list.bar-width", 150, 50, 1000);
        barHeight = new IntSetting(persistence, "cast-list.bar-height", 20, 10, 100);
        reverse = new BooleanSetting(persistence, "cast-list.reverse", false);
        showAbilityId = new BooleanSetting(persistence, "cast-list.show-ability-id", false);
    }

    public IntSetting getMaxDisplayedBars() {
        return maxDisplayedBars;
    }

    public IntSetting getBarWidth() {
        return barWidth;
    }

    public IntSetting getBarHeight() {
        return barHeight;
    }

    public BooleanSetting getReverse() {
        return reverse;
    }

    public BooleanSetting getShowAbilityId() {
        return showAbilityId;
    }
}
