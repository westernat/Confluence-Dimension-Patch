package org.mesdag.confluence_dimension_patch.mixed;

public interface IDimensionAccessor {
    void cdp$setIsNotOverworld();

    boolean cdp$isNotOverworld();

    default boolean cdp$isOverworld() {
        return !cdp$isNotOverworld();
    }

    static IDimensionAccessor of(Object o) {
        return (IDimensionAccessor) o;
    }
}
