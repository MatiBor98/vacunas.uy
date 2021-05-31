package converters;


import beans.Constantes;
import io.jsonwebtoken.lang.Strings;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;

@FacesConverter(value = WeekConverter.ID)
public class WeekConverter extends DateTimeConverter {
    public static final String ID = "converters.WeekConverter";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        return Strings.hasText(value) ? LocalDate.parse(value + "-1", DateTimeFormatter.ISO_WEEK_DATE) : null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        return value == null ? null : ((LocalDate) value).format(DateTimeFormatter.ISO_WEEK_DATE).substring(0, 8);
    }
}