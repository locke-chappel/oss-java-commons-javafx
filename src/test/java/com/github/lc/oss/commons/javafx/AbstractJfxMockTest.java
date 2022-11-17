package com.github.lc.oss.commons.javafx;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import com.github.lc.oss.commons.testing.AbstractMockTest;
import com.sun.glass.ui.Screen;

public abstract class AbstractJfxMockTest extends AbstractMockTest {
    protected Screen screen;

    @BeforeEach
    public void initJfx() {
        this.screen = Mockito.mock(Screen.class);
        Field screensField = this.findField("screens", Screen.class);
        this.setField(screensField, Arrays.asList(this.screen), null);
    }
}
