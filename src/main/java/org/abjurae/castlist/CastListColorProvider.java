package org.abjurae.castlist;

import java.awt.Color;

public interface CastListColorProvider {
    Color getFontColor();
    Color getActiveColor();
    Color getTargetedColor();
    Color getHiddenActorColor();
}
