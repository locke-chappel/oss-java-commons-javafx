package io.github.lc.oss.commons.javafx.views;

import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.lc.oss.commons.javafx.AbstractJfxMockTest;
import io.github.lc.oss.commons.javafx.services.L10N;
import io.github.lc.oss.commons.l10n.UserLocale;

public class L10NServiceTest extends AbstractJfxMockTest {
    @Test
    public void test_getTextNoVars() {
        L10N service = new L10N();

        String result = service.getTextNoVars("anything");
        Assertions.assertEquals("??? anything ???", result);
    }

    @Test
    public void test_getText_noLocale() {
        L10N service = new L10N();

        String result = service.getText("anything");
        Assertions.assertEquals("??? anything ???", result);
    }

    @Test
    public void test_getText_userLocale() {
        L10N service = new L10N() {
            @Override
            public UserLocale getUserLocale() {
                return new UserLocale(Locale.GERMAN);
            }
        };

        String result = service.getText("anything");
        Assertions.assertEquals("??? anything ???", result);
    }
}
