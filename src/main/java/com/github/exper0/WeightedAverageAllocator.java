package com.github.exper0;

import com.google.common.base.Objects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.Function;

/**
 * @author Andrei Eliseev (aeg.exper0@gmail.com)
 */
public class WeightedAverageAllocator<R, D>  implements
    TransportationProblem<WeightedAverageAllocator.A<R>,WeightedAverageAllocator.A<D>, WeightedAverageAllocator.Holder> {
    private static final int SIZE = 128;
    private static final int PRECISION = 12;

    public Collection<Allocation<R, D>> allocate(
        Collection<R> resources,
        Collection<D> demands,
        Function<R, BigDecimal> rv,
        Function<R, Integer> rq,
        Function<D, Integer> dq) {
        int total = resources.stream().mapToInt(rq::apply).sum();
        Table<A<R>, A<D>, Holder> matrix = HashBasedTable.create(SIZE, SIZE);
        for (D demand: demands) {
            int remainingDemand = dq.apply(demand);
            for (R resource: resources) {
                BigDecimal priceLevel = rv.apply(resource);
                Integer quantity = rq.apply(resource);
                BigDecimal roundedVal = new BigDecimal(remainingDemand * ((double)quantity / total)).setScale(0, BigDecimal.ROUND_DOWN);
            }
        }
    }

    @Override
    public Collection<Allocation<A<R>, A<D>>> solve(Table<A<R>, A<D>, Holder> matrix, Function<A<R>, Integer> r, Function<A<D>, Integer> d) {
        return null;
    }

    static class Holder {
        public BigDecimal penalty;
        public int roundedValue;
        public int finalValue;
        public int toBeAllocatedValue;
    }

    static class A<T> {
        final T item;
        int toBeAllocated;

        public W(T item, int toBeAllocated) {
            this.item = item;
            this.toBeAllocated = toBeAllocated;
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return item.equals(obj);
        }
    }
}
