package com.company.qldp.common.util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.Function;

public class ReactiveUtils {
    
    public static <T> Flux<T> intersect(Flux<T> f1, Flux<T> f2) {
        return f1.join(f2, f -> Flux.never(), f -> Flux.never(), Tuples::of)
            .filter(t -> t.getT1().equals(t.getT2()))
            .map(Tuple2::getT1)
            .groupBy(Function.identity())
            .map(GroupedFlux::key);
    }
}
