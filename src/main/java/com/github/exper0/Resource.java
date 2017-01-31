package com.github.exper0;

import java.math.BigDecimal;

/**
 * @author Andrei Eliseev (aeg.exper0@gmail.com)
 * @version $Id$
 */
public interface Resource<V> {
    V value();
    int quantity();
}
