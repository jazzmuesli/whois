package net.ripe.db.whois.common.collect;

import com.google.common.collect.Iterables;
import net.ripe.db.whois.common.domain.Identifiable;
import net.ripe.db.whois.common.domain.ResponseObject;
import net.ripe.db.whois.common.rpsl.RpslObject;

import java.util.Collection;
import java.util.Objects;

public final class CollectionHelper {

    public static final byte[] EMPTY_BYTE_ARRAY = new byte[] {};

    private CollectionHelper() {
        // do not instantiate
    }

    public static <T> T uniqueResult(final Collection<T> c) {
        switch (c.size()) {
            case 0:
                return null;
            case 1:
                return c.iterator().next();
            default:
                throw new IllegalStateException("Unexpected number of elements in collection: " + c.size());
        }
    }

    // TODO: [AH] result is wrapped by 2 iterable wrappers in this method - optimize!
    public static Iterable<ResponseObject> iterateProxy(
            final ProxyLoader<Identifiable,
                    RpslObject> rpslObjectLoader,
            final Iterable<? extends Identifiable> identifiables) {
        final ProxyIterable<Identifiable, ? extends ResponseObject> rpslObjects =
                new ProxyIterable<>((Iterable<Identifiable>) identifiables, rpslObjectLoader, 100);
        return Iterables.filter((Iterable<ResponseObject>)rpslObjects, (Objects::nonNull));
    }
}
