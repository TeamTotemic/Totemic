package pokefenn.totemic.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;

public final class MiscUtil {
    /**
     * Returns a Collector collecting the maximal elements from a Stream into a List. Useful if there will generally be
     * multiple maximal elements and all of them need to be collected.
     * @param <T> the type of the input elements
     * @param comp a Comparator for comparing elements
     */
    public static <T> Collector<T, ?, List<T>> collectMaxElements(Comparator<? super T> comp) {
        return Collector.of(
                () -> new ArrayList<>(2), //the list is usually going to be small
                (List<T> list, T e) -> {
                    if(list.isEmpty())
                        list.add(e);
                    else {
                        int c = comp.compare(e, list.get(0));
                        if(c == 0) {
                            list.add(e);
                        }
                        else if(c > 0) {
                            list.clear();
                            list.add(e);
                        }
                    }
                },
                (List<T> list1, List<T> list2) -> {
                    if(list1.isEmpty())
                        return list2;
                    else if(list2.isEmpty())
                        return list1;
                    else {
                        int c = comp.compare(list1.get(0), list2.get(0));
                        if(c == 0) {
                            list1.addAll(list2);
                            return list1;
                        }
                        else if(c > 0)
                            return list1;
                        else
                            return list2;
                    }
                });
    }
}
