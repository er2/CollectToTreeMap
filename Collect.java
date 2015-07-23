import java.util.Comparator;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;

public class Collect {

    public static <T, K extends Comparable<? super K>, V>
            Collector<T, TreeMap<K, V>, TreeMap<K, V>>
            toTreeMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return Collector.of(TreeMap<K, V>::new,
                            (TreeMap<K, V> tm, T t) -> tm.put(keyMapper.apply(t), valueMapper.apply(t)),
                            (TreeMap<K, V> tm1, TreeMap<K, V> tm2) -> {
                                tm1.putAll(tm2);
                                return tm1;
                            },
                            Collector.Characteristics.IDENTITY_FINISH,
                            Collector.Characteristics.UNORDERED);
    }

    public static <T, K extends Comparable<? super K>, V>
            Collector<T, TreeMap<K, V>, TreeMap<K, V>>
            toTreeMap(Function<? super T, ? extends K> keyMapper,
                      Function<? super T, ? extends V> valueMapper,
                      Comparator<? super K> comparator) {
        return Collector.of(() -> new TreeMap<K, V>(comparator),
                            (TreeMap<K, V> tm, T t) -> tm.put(keyMapper.apply(t), valueMapper.apply(t)),
                            (TreeMap<K, V> tm1, TreeMap<K, V> tm2) -> {
                                tm1.putAll(tm2);
                                return tm1;
                            },
                            Collector.Characteristics.IDENTITY_FINISH,
                            Collector.Characteristics.UNORDERED);
    }

    public static <T, K extends Comparable<? super K>, V>
            Collector<T, TreeMap<K, V>, TreeMap<K, V>>
            toTreeMap(Function<? super T, ? extends K> keyMapper,
                      Function<? super T, ? extends V> valueMapper,
                      BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return Collector.of(TreeMap<K, V>::new,
                            (TreeMap<K, V> tm, T t) -> tm.put(keyMapper.apply(t), valueMapper.apply(t)),
                            (TreeMap<K, V> tm1, TreeMap<K, V> tm2) -> {
                                tm2.forEach((k, v) -> tm1.merge(k, v, remappingFunction));
                                return tm1;
                            },
                            Collector.Characteristics.IDENTITY_FINISH,
                            Collector.Characteristics.UNORDERED);
    }

    public static <T, K extends Comparable<? super K>, V>
            Collector<T, TreeMap<K, V>, TreeMap<K, V>>
            toTreeMap(Function<? super T, ? extends K> keyMapper,
                      Function<? super T, ? extends V> valueMapper,
                      Comparator<? super K> comparator,
                      BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return Collector.of(() -> new TreeMap<K, V>(comparator),
                            (TreeMap<K, V> tm, T t) -> tm.put(keyMapper.apply(t), valueMapper.apply(t)),
                            (TreeMap<K, V> tm1, TreeMap<K, V> tm2) -> {
                                tm2.forEach((K key, V value) -> tm1.merge(key, value, remappingFunction));
                                return tm1;
                            },
                            Collector.Characteristics.IDENTITY_FINISH,
                            Collector.Characteristics.UNORDERED);
    }
}
