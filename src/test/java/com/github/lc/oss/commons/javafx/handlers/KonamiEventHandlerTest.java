package com.github.lc.oss.commons.javafx.handlers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KonamiEventHandlerTest {
    private static class TestAction implements KonamiEventHandler.Action {
        public boolean called = false;

        @Override
        public void execute() {
            this.called = true;
        }

    }

    private static final KeyEvent UP = new KeyEvent(null, null, null, null, null, KeyCode.UP, false, false, false, false);
    private static final KeyEvent DOWN = new KeyEvent(null, null, null, null, null, KeyCode.DOWN, false, false, false, false);
    private static final KeyEvent LEFT = new KeyEvent(null, null, null, null, null, KeyCode.LEFT, false, false, false, false);
    private static final KeyEvent RIGHT = new KeyEvent(null, null, null, null, null, KeyCode.RIGHT, false, false, false, false);
    private static final KeyEvent A = new KeyEvent(null, null, null, null, null, KeyCode.A, false, false, false, false);
    private static final KeyEvent B = new KeyEvent(null, null, null, null, null, KeyCode.B, false, false, false, false);
    private static final KeyEvent C = new KeyEvent(null, null, null, null, null, KeyCode.C, false, false, false, false);

    @Test
    public void test_handleKeys() {
        TestAction action = new TestAction();

        KonamiEventHandler handler = new KonamiEventHandler(action);
        handler.handle(KonamiEventHandlerTest.C);
        Assertions.assertFalse(action.called);

        handler.handle(KonamiEventHandlerTest.UP);
        handler.handle(KonamiEventHandlerTest.UP);
        handler.handle(KonamiEventHandlerTest.DOWN);
        handler.handle(KonamiEventHandlerTest.DOWN);
        handler.handle(KonamiEventHandlerTest.LEFT);
        handler.handle(KonamiEventHandlerTest.RIGHT);
        handler.handle(KonamiEventHandlerTest.LEFT);
        handler.handle(KonamiEventHandlerTest.RIGHT);
        handler.handle(KonamiEventHandlerTest.B);
        Assertions.assertFalse(action.called);
        handler.handle(KonamiEventHandlerTest.A);
        Assertions.assertTrue(action.called);
    }
}
