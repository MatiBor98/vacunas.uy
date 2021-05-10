package logica.creacion;

public interface Converter<S, T> {
    T convert(S source);
}
