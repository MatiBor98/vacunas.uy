package converters;

import org.apache.commons.lang3.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;
import java.time.LocalTime;

@FacesConverter(value = LocalTimeConverter.ID)
public class LocalTimeConverter extends DateTimeConverter {
    public static final String ID = "facultad.tse.converters.LocalTimeConverter";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        return StringUtils.isBlank(value) ? null : LocalTime.parse(value);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        return value == null ? null : value.toString();
    }
}