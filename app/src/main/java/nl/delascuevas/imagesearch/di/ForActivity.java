package nl.delascuevas.imagesearch.di;

/**
 * Created by juanma on 23/03/15.
 */

import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier @Retention(RUNTIME)
public @interface ForActivity {
}