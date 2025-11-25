package org.abjurae.castlist;

import gg.xp.reevent.scan.ScanMe;
import gg.xp.xivsupport.persistence.PersistenceProvider;
import gg.xp.xivsupport.persistence.settings.ColorSetting;

import java.awt.*;

@ScanMe
public class CastListColorProviderImpl implements CastListColorProvider {
    private static final Color defaultColorActive = new Color(136, 0, 0, 255);
    private static final Color defaultColorTargeted = new Color(0, 27, 136, 255);
    private static final Color defaultOtherColorTargeted = new Color(94, 0, 181, 255);
    private static final Color defaultColorFont = new Color(240, 240, 240);
    private static final Color defaultColorHiddenActor = new Color(19, 89, 0, 255);
    private final ColorSetting active;
    private final ColorSetting targeted;
    private final ColorSetting otherTargeted;
    private final ColorSetting font;
    private final ColorSetting hiddenActor;

    public CastListColorProviderImpl(PersistenceProvider pers) {
        this.active = new ColorSetting(pers, "cast-list-overlay.colors.active", defaultColorActive);
        this.targeted = new ColorSetting(pers, "cast-list-overlay.colors.targeted", defaultColorTargeted);
        this.otherTargeted = new ColorSetting(pers, "cast-list-overlay.colors.other-targeted", defaultOtherColorTargeted);
        this.hiddenActor = new ColorSetting(pers, "cast-list-overlay.colors.hidden-actor", defaultColorHiddenActor);
        this.font = new ColorSetting(pers, "cast-list-overlay.colors.font", defaultColorFont);
    }

    @Override
    public Color getFontColor() {
        return font.get();
    }

    @Override
    public Color getTargetedColor() {
        return targeted.get();
    }

    @Override
    public Color getOtherTargetedColor() {
        return otherTargeted.get();
    }

    @Override
    public Color getActiveColor() {
        return active.get();
    }

    @Override
    public Color getHiddenActorColor() {
        return hiddenActor.get();
    }

    public ColorSetting getActiveSetting() {
        return active;
    }

    public ColorSetting getTargetedSetting() {
        return targeted;
    }

    public ColorSetting getOtherTargetedSetting() {
        return otherTargeted;
    }

    public ColorSetting getFontSetting() {
        return font;
    }

    public ColorSetting getHiddenActorSetting() {
        return hiddenActor;
    }
}
