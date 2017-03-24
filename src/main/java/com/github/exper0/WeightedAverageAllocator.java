package com.github.exper0;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Andrei Eliseev (aeg.exper0@gmail.com)
 */
public class WeightedAverageAllocator<R, D>  implements
    TransportationProblem<WeightedAverageAllocator.A<R>,WeightedAverageAllocator.A<D>, WeightedAverageAllocator.Holder> {
    private static final int SIZE = 128;
    private static final int PRECISION = 15;
    private static final int ROUNDING = BigDecimal.ROUND_HALF_UP;
//    private final Map<D, IntHolder> remainingDemands = new HashMap<D, IntHolder>(SIZE);
//    private final Map<R, IntHolder> remainingResources = new HashMap<R, IntHolder>(SIZE);

    public Collection<Allocation<R, D>> allocate(
        Collection<R> resources,
        Collection<D> demands,
        Function<R, BigDecimal> rv,
        Function<R, Integer> rq,
        Function<D, Integer> dq) {
        int total = resources.stream().mapToInt(rq::apply).sum();
        BigDecimal sum = resources.stream().map(r->rv.apply(r).multiply(new BigDecimal(rq.apply(r)))).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal average = sum.divide(new BigDecimal(total), PRECISION, ROUNDING);
        Table<A<R>, A<D>, Holder> matrix = HashBasedTable.create(SIZE, SIZE);
        for (D demand: demands) {
            A<D> wrappedDemand = new A<D>(demand, dq.apply(demand));
            Map<A<R>, Holder> remainingResources = matrix.column(wrappedDemand);
            for (R resource: resources) {
                A<R> wrappedResource = new A<R>(resource, rq.apply(resource));
                BigDecimal priceLevel = rv.apply(resource);
                BigDecimal roundedVal = new BigDecimal(wrappedDemand.quantity * ((double)wrappedResource.quantity / total)).setScale(0, BigDecimal.ROUND_DOWN);
                Holder holder = new Holder();
                holder.penalty = priceLevel.subtract(average).divide(new BigDecimal(wrappedResource.quantity), PRECISION, ROUNDING);
                holder.roundedValue = roundedVal.intValue();
                wrappedDemand.allocate(holder.roundedValue);
                wrappedResource.allocate(holder.roundedValue);
                remainingResources.put(wrappedResource, holder);
            }
        }
        return null;
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
        final int quantity;
        int remaining;

        public A(T item, int quantity) {
            this.item = item;
            this.quantity = quantity;
            this.remaining = quantity;
        }

        public int allocate(int amount) {
            this.remaining -= amount;
            return this.remaining;
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
