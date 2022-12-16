package com.github.iarellano.j2y2j;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Separators;

public class NoSpaceBeforeColonPrettyPrinter extends DefaultPrettyPrinter {

    @Override
    public DefaultPrettyPrinter createInstance() {
        return new NoSpaceBeforeColonPrettyPrinter();
    }

    @Override
    public NoSpaceBeforeColonPrettyPrinter withSeparators(Separators separators) {
        _separators = separators;
        _objectFieldValueSeparatorWithSpaces = separators.getObjectFieldValueSeparator() + " ";
        return this;
    }
}
