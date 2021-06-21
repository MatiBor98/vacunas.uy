package converters;

import org.apache.commons.lang3.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;
import java.time.LocalDate;

@FacesConverter(value = LocalDateConverter.ID)
public class LocalDateConverter extends DateTimeConverter {
    public static final String ID = "facultad.tse.converters.LocalDateConverter";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        return StringUtils.isBlank(value) ? null : LocalDate.parse(value);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        return value == null ? null : value.toString();
    }
}