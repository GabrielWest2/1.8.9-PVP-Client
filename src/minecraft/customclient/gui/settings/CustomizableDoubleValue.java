package customclient.gui.settings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomizableDoubleValue {
	String uiName();
	float min();
	float max();
	double defaultValue();
}
