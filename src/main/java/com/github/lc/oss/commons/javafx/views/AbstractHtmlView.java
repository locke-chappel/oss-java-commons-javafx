package com.github.lc.oss.commons.javafx.views;

import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.github.lc.oss.commons.javafx.services.L10N;
import com.github.lc.oss.commons.javafx.services.ResourceService;
import com.github.lc.oss.commons.l10n.Variable;
import com.github.lc.oss.commons.web.resources.AbstractResourceResolver.Types;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public abstract class AbstractHtmlView {
    private WebView view;

    protected abstract String getViewName();

    protected String getHtmlPath() {
        return this.getViewName() + ".html";
    }

    public void apply(Stage stage) {
        this.init();
        stage.setTitle(this.getText(this.getTitleId()));
        stage.setMinWidth(this.getMiniumWidth());
        stage.setHeight(this.getMinimumHeight());
        stage.setScene(new Scene(this.getView(), this.getMiniumWidth(), this.getMinimumHeight(), true, SceneAntialiasing.BALANCED));
    }

    protected WebView getView() {
        if (this.view == null) {
            this.view = new WebView();
        }
        return this.view;
    }

    protected String getCSS() {
        if (this.getResourceService() == null) {
            return null;
        }

        return this.getResourceService().get(Types.css);
    }

    protected void init() {
        this.loadHtml(this.getHtmlPath());
        this.registerController("L10N", this.getL10N());
        this.registerController(this.getViewName(), this, this.getJavaScriptControllerParentId());
    }

    protected String getJavaScript() {
        if (this.getResourceService() == null) {
            return null;
        }

        return this.getResourceService().get(Types.js);
    }

    protected void loadHtml(String path) {
        this.getView().getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                if (newValue == State.SUCCEEDED) {
                    Document doc = AbstractHtmlView.this.getView().getEngine().getDocument();
                    NodeList heads = doc.getDocumentElement().getElementsByTagName("head");
                    Element head = null;
                    if (heads.getLength() < 1) {
                        head = doc.createElement("head");
                        doc.appendChild(head);
                    } else {
                        head = (Element) heads.item(0);
                    }

                    String css = AbstractHtmlView.this.getCSS();
                    Element style = doc.createElement("style");
                    Text content = doc.createTextNode(css);
                    style.appendChild(content);
                    head.appendChild(style);

                    String js = AbstractHtmlView.this.getJavaScript();
                    js += AbstractHtmlView.this.getResourceService().getPageScript(AbstractHtmlView.this.getViewName());
                    js += "$$.Init.Run();";
                    AbstractHtmlView.this.getView().getEngine().executeScript(js);
                }
            }
        });

        URL url = this.getClass().getResource(this.getHtmlRoot() + path);
        this.getView().getEngine().load(url.toString());
    }

    protected void registerController(String id, final Object controller) {
        this.registerController(id, controller, "window");
    }

    protected void registerController(String id, final Object controller, String parentId) {
        this.getView().getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                if (newValue == State.SUCCEEDED) {
                    JSObject window = (JSObject) AbstractHtmlView.this.getView().getEngine().executeScript(parentId);
                    window.setMember(id, controller);
                    AbstractHtmlView.this.getView().getEngine().getLoadWorker().stateProperty().removeListener(this);
                }
            }
        });
    }

    protected String getText(String id, Variable... vars) {
        if (this.getL10N() == null) {
            return id;
        }
        return this.getL10N().getText(id, vars);
    }

    protected ResourceService getResourceService() {
        return null;
    }

    protected String getJavaScriptControllerParentId() {
        return "window";
    }

    protected String getHtmlRoot() {
        return "/templates/views/";
    }

    protected int getMiniumWidth() {
        return 720;
    }

    protected int getMinimumHeight() {
        return 320;
    }

    protected String getTitleId() {
        return null;
    }

    protected L10N getL10N() {
        return null;
    }
}
