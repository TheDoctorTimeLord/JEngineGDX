package ru.jengine.jenginegdx.viewmodel.stateimporting.linking;

import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.LinkField.MultiLink;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.LinkField.SingleLink;

import java.util.Arrays;

public sealed class LinkField permits SingleLink, MultiLink {
    private final String field;

    public LinkField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public static final class SingleLink extends LinkField {
        private final String link;

        public SingleLink(String field, String link) {
            super(field);
            this.link = link;
        }

        public String getLink() {
            return link;
        }

        @Override
        public String toString() {
            return "SingleLink{" +
                    "field='" + getField() + '\'' +
                    ", link='" + link + '\'' +
                    '}';
        }
    }

    public static final class MultiLink extends LinkField {
        private final String[] links;

        public MultiLink(String field, String[] links) {
            super(field);
            this.links = links;
        }

        public String[] getLinks() {
            return links;
        }

        @Override
        public String toString() {
            return "MultiLink{" +
                    "field='" + getField() + '\'' +
                    ", links=" + Arrays.toString(links) +
                    '}';
        }
    }
}
