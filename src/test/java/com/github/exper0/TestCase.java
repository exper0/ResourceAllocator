package com.github.exper0;

import java.util.Collection;

/**
 * @author Andrei Eliseev (aeg.exper0@gmail.com)
 */
public class TestCase {
    private Collection<TestEntry> resources;
    private Collection<TestEntry> demands;

    public Collection<TestEntry> getResources() {
        return resources;
    }

    public void setResources(Collection<TestEntry> resources) {
        this.resources = resources;
    }

    public Collection<TestEntry> getDemands() {
        return demands;
    }

    public void setDemands(Collection<TestEntry> demands) {
        this.demands = demands;
    }
}
