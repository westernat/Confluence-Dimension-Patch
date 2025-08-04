package org.mesdag.confluence_dimension_patch.common;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class CDPCommonConfigs {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue INITIAL_TERRARIUM = BUILDER.define("initialTerrarium", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
