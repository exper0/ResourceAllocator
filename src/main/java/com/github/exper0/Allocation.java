package com.github.exper0;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Andrei Eliseev (aeg.exper0@gmail.com)
 */
public interface Allocation<R, D> {
//    R resource();
    Collection<AllocatedResource<R>> resources();
    D demand();
    int quantity();

    class AllocatedResource<R> {
        public final R resource;
        public final int quantity;

        public AllocatedResource(R resource, int quantity) {
            this.resource = resource;
            this.quantity = quantity;
        }
    }
}
