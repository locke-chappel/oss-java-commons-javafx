package com.github.lc.oss.commons.javafx.handlers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KonamiEventHandler implements EventHandler<KeyEvent> {
    public static interface Action {
        void execute();
    }

    private static final List<KeyCode> KONAMI_CODE = Collections.unmodifiableList(Arrays.asList(//
            KeyCode.UP, KeyCode.UP, //
            KeyCode.DOWN, KeyCode.DOWN, //
            KeyCode.LEFT, KeyCode.RIGHT, //
            KeyCode.LEFT, KeyCode.RIGHT, //
            KeyCode.B, KeyCode.A));

    private final Action action;
    private int position = 0;

    public KonamiEventHandler(Action action) {
        this.action = action;
    }

    @Override
    public void handle(KeyEvent event) {
        if (KonamiEventHandler.KONAMI_CODE.get(this.position) == event.getCode()) {
            this.position++;
        } else {
            this.position = 0;
        }

        if (this.position >= KonamiEventHandler.KONAMI_CODE.size()) {
            this.action.execute();
            this.position = 0;
        }
    }
}
