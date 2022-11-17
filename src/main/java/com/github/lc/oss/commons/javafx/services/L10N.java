package com.github.lc.oss.commons.javafx.services;

import java.util.Locale;

import com.github.lc.oss.commons.l10n.UserLocale;
import com.github.lc.oss.commons.l10n.Variable;

public class L10N extends com.github.lc.oss.commons.l10n.L10N {
    /**
     * Special signature for JavaScript to Java via WebView - variable argument
     * functions aren't recognized correctly.. :(
     */
    public String getTextNoVars(String id) {
        return this.getText(this.getLocale(), id);
    }

    public String getText(String id, Variable... vars) {
        return this.getText(this.getLocale(), id, vars);
    }

    protected Locale getLocale() {
        return this.getUserLocale() == null ? Locale.ENGLISH : this.getUserLocale().getLocale();
    }

    public UserLocale getUserLocale() {
        return null;
    }
}
