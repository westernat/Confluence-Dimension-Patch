package org.mesdag.confluence_dimension_patch.mixed;

public interface IDimensionAccessor {
    void confluence_dimension_patch$setIsNotOverworld();

    boolean confluence_dimension_patch$isNotOverworld();

    static IDimensionAccessor of(Object o) {
        return (IDimensionAccessor) o;
    }
}
