package ru.jengine.jenginegdx.viewmodel.stateimporting;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

public enum CoreNamespace {
    INTERNAL("coreInternal", "int"),
    LOCAL("coreLocal", "loc");

    private final Set<String> aliases;

    CoreNamespace(String namespace, String... aliases) {
        this.aliases = Stream.concat(Stream.of(namespace), Arrays.stream(aliases))
                .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);
    }

    public String getNamespace() {
        return aliases.iterator().next();
    }

    @Nullable
    public static CoreNamespace of(String namespace) {
        for (CoreNamespace value : values()) {
            if (value.aliases.contains(namespace)) {
                return value;
            }
        }
        return null;
    }
}
