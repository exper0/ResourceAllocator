package com.github.exper0;

import com.google.common.collect.Table;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author Andrei Eliseev (aeg.exper0@gmail.com)
 * @version $Id$
 */
public interface TransportationProblem<R, D, W> {
    Collection<Allocation<R, D>> solve(
        Table<R, D, W> matrix,
        Function<R, Integer> r,
        Function<D, Integer> d);
}
