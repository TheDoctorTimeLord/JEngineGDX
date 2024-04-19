package ru.jengine.jenginegdx.container.jsonreading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.stateimporting.CoreNamespace;
import ru.jengine.jsonconverter.exceptions.ResourceLoadingException;
import ru.jengine.jsonconverter.resources.ResourceLoaderWithCache;
import ru.jengine.jsonconverter.resources.ResourceMetadata;

import javax.annotation.Nullable;
import java.nio.file.FileSystems;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Bean
public class JsonResourceLoader extends ResourceLoaderWithCache {
    @Override
    protected String getResourceInt(ResourceMetadata metadata) throws ResourceLoadingException {
        FileHandle file = asFile(metadata);
        if (file == null) {
            throw new ResourceLoadingException("Resource [%s] is not found".formatted(metadata));
        }
        return file.readString();
    }

    @Override
    public boolean hasResourceByMetadata(ResourceMetadata metadata) {
        FileHandle file = asFile(metadata);
        return file != null && file.exists() && !file.isDirectory();
    }

    @Nullable
    private static FileHandle asFile(ResourceMetadata metadata) {
        CoreNamespace coreNamespace = CoreNamespace.of(metadata.getNamespace());
        if (coreNamespace == null) {
            return null;
        }

        String path = Stream.concat(Stream.of(metadata.getObjectType()), metadata.getPath().stream())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(FileSystems.getDefault().getSeparator()));

        return switch (coreNamespace) {
            case LOCAL -> Gdx.files.local(path);
            case INTERNAL -> Gdx.files.internal(path);
        };
    }
}
