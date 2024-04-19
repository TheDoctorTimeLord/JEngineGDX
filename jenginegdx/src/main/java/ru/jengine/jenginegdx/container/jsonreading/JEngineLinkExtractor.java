package ru.jengine.jenginegdx.container.jsonreading;

import org.jetbrains.annotations.Nullable;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.viewmodel.stateimporting.CoreNamespace;
import ru.jengine.jsonconverter.linking.LinkExtractor;
import ru.jengine.jsonconverter.resources.ResourceMetadata;

import java.util.Arrays;
import java.util.stream.Stream;

@Shared
public class JEngineLinkExtractor implements LinkExtractor {
    private static final String[] EMPTY_PATH = new String[0];

    @Nullable
    @Override
    public ResourceMetadata extractLink(String rawLink) {
        String[] parts = rawLink.split(":");
        if (parts.length < 2 || parts.length > 3) {
            return null;
        }
        String namespace = extractNamespace(parts[0]);
        String objectType = null;
        String[] path;

        if (parts.length == 2) {
            path = extractPath(parts[1]);
        }
        else {
            objectType = parts[1].strip();
            path = extractPath(parts[2]);
        }

        return new ResourceMetadata(namespace, objectType, path);
    }

    protected String extractNamespace(String part) {
        String rawNamespace = part.strip();
        CoreNamespace namespace = CoreNamespace.of(rawNamespace);
        return namespace != null ? namespace.getNamespace() : null;
    }

    private static String[] extractPath(String part) {
        int pathLocationSeparator = part.indexOf("$");
        String[] fileSystemPath = pathLocationSeparator == -1
                ? part.split("/")
                : part.substring(0, pathLocationSeparator).split("/");
        String[] internalPath = pathLocationSeparator == -1
                ? EMPTY_PATH
                : part.substring(pathLocationSeparator + 1).split("\\.");
        return Stream.concat(Arrays.stream(fileSystemPath), Arrays.stream(internalPath)).toArray(String[]::new);
    }
}
