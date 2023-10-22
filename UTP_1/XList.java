package zad1;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class XList<T> extends ArrayList<T> {
    private static ArrayList temp = new ArrayList();

    public XList(Collection<T> coll) {
        super(coll);
    }

    @SafeVarargs
    public XList(T... elem) { super(Arrays.asList(elem));}

    public static<T> XList<T> of(Collection<T> coll) { return new XList<T>(coll); }

    @SafeVarargs
    public static <T> XList<T> of(T... elem) { return new XList<T>(elem); }

    public static<T> XList<T> charsOf(String str){
        temp = new ArrayList();
        for (int i = 0; i < str.length(); i++) {
            temp.add(String.valueOf(str.charAt(i)));
        }
        return new XList<T>(temp);
    }
    public static<T> XList<T> tokensOf(String...str) {
        temp = new ArrayList();
        if(str.length == 1) {
            temp = new ArrayList<>(Arrays.asList(str[0].split("\\s")));
        }else {
            temp = new ArrayList<>(Arrays.asList(str[0].split(str[str.length - 1])));
        }
        return new XList<T>(temp);
    }

    public XList<T> union(T... elem) {
        temp = new ArrayList(this);
        temp.addAll(Arrays.asList(elem));

        return new XList(temp);
    }

    public XList<T> union(Collection<T> coll) {
        temp = new ArrayList(this);
        temp.addAll(coll);

        return new XList(temp);
    }

    public XList<T> diff(Collection<T> coll) {
        Collections.copy(temp, this);
        temp.removeAll(coll);

        return new XList<T>(temp);
    }

    public XList<T> unique() {
        temp = this.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

        return new XList<T>(temp);
    }

//    public XList<T> combine() {
//       temp = new ArrayList();
//       ArrayList temp2 = new ArrayList(this);
//        ArrayList<Object> collect =
//                temp2.stream()
//                        .flatMap(List::stream)
//                        .collect(Collectors.toList());
//
//
//
//        return new XList<T>(temp);
//    }

    public <R> XList<R> collect(Function<T, R> func) {
        temp = new ArrayList<R>();
        for (Object obj : temp) {
            temp.add(func.apply((T) obj));
        }
        return new XList<R>(temp);
    }

    public String join(String str) {
        StringBuffer joiner = new StringBuffer();
        for (T t : this) {
            joiner.append(t.toString() + str);
        }
        return joiner.toString();
    }

    public String join() {
        StringBuffer joiner = new StringBuffer();
        for (T t : this) {
            joiner.append(t.toString() + " ");
        }
        return joiner.toString();
    }

    public void forEachWithIndex(BiConsumer<T,Integer> cons) {
        for (int i = 0; i < this.size(); i++) {
            cons.accept(this.get(i), i);
        }
    }
}
