package datos.entidades;

public enum EstadoAprobacion {
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
    ACEPTADA("bg-success") {
        @Override public <T> T visit(Visitor<T> visitor) {
            return visitor.aceptada();
        }
    };

    private final String cssClass;

    EstadoAprobacion(String cssClass) {
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
        default T aceptada() {
            return null;
        }
    }
}
