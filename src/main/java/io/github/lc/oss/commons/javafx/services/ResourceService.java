package io.github.lc.oss.commons.javafx.services;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.lc.oss.commons.util.IoTools;
import io.github.lc.oss.commons.util.PathNormalizer;
import io.github.lc.oss.commons.web.resources.AbstractResourceResolver;
import io.github.lc.oss.commons.web.resources.StaticResourceFileResolver;

public class ResourceService extends AbstractResourceResolver {
    private static final String DEFAULT_APP_RESOURCE_PATH = new PathNormalizer().dir("static-secure/");
    private static final String JFX_LIBRARY_PATH = "jfx-library/";

    protected static final StaticResourceFileResolver JFX_LIBRARY_RESOLVER = new StaticResourceFileResolver(ResourceService.JFX_LIBRARY_PATH, 3);

    protected String getAppResourcePath() {
        return ResourceService.DEFAULT_APP_RESOURCE_PATH;
    }

    protected String getExternalResourcePath() {
        return null;
    }

    protected int getSearchDepth() {
        return 5;
    }

    @Override
    protected String getContextPath() {
        return this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
    }

    @Override
    protected List<StaticResourceFileResolver> getFileResolvers() {
        if (this.resolvers == null) {
            int depth = this.getSearchDepth();
            String path = this.getAppResourcePath();
            StaticResourceFileResolver appResolver = path == null ? null : new StaticResourceFileResolver(path, depth);
            path = this.getExternalResourcePath();
            StaticResourceFileResolver extResolver = path == null ? null : new StaticResourceFileResolver(path, depth);

            List<StaticResourceFileResolver> resolvers = Arrays.asList( //
                    AbstractResourceResolver.LIBRARY_RESOLVER, //
                    ResourceService.JFX_LIBRARY_RESOLVER, //
                    appResolver, //
                    extResolver);

            this.resolvers = resolvers.stream(). //
                    filter(r -> r != null). //
                    collect(Collectors.toUnmodifiableList());
        }
        return this.resolvers;
    }

    public String get(Types type) {
        if (type == null) {
            throw new RuntimeException("Type cannot be null");
        }

        switch (type) {
            case css:
            case js:
                /* valid, nothing to do */
                break;
            default:
                throw new RuntimeException("This version of get() only supports CSS and JavaScript.");
        }

        String content = this.compile(type);
        content = this.replaceValues(type, content);
        return content;
    }

    public String getPageScript(String page) {
        List<String> scripts = IoTools.listDir( //
                AbstractResourceResolver.LIBRARY_PATH + "js-templates/", //
                1, //
                path -> path.toString().endsWith("lib-page.js"));
        byte[] bytes = IoTools.readAbsoluteFile(scripts.iterator().next());
        String script = new String(bytes, StandardCharsets.UTF_8);

        script = this.replaceValues(Types.js, script);
        script = script.replace("%Page%", page);
        return script;
    }

    @Override
    protected String resolveValue(Types type, String key, Map<String, String> extraValues) {
        switch (key) {
            case "context.path.resource":
                return "../../static-library/";
            case "context.path.url":
            default:
                return super.resolveValue(type, key, extraValues);
        }
    }
}
