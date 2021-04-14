// -----------------------
// Author ~ IllgiLP
// Rewritten by Pandadoxo
// on 16.08.2020 at 16:19 
// -----------------------

package de.meloneoderso.melonencb.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Exclude {
}
