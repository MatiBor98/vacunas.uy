package datos.entidades;

public enum Estado {
	PENDIENTE("bg-warning text-dark") {
        @Override public <T> T visit(Visitor<T> visitor) {
            return visitor.pendiente();
        }
    },
    CANCELADA("bg-danger") {
        @Override public <T> T visit(Visitor<T> visitor) {
            return visitor.cancelada();
        }
    },
    VACUNADO("bg-success") {
        @Override public <T> T visit(Visitor<T> visitor) {
            return visitor.vacunado();
        }
    };

    private final String cssClass;

    Estado(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }

    public abstract <T> T visit(Visitor<T> visitor);

    public interface Visitor<T> {
        default T pendiente() {
            return null;
        }
        default T cancelada() {
            return null;
        }
        default T vacunado() {
            return null;
        }
    }
}
