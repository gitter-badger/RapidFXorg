package de.github.yfons.rapidfx.rapidFX.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.github.yfons.rapidfx.rapidFX.core.RapidFX;

/**
 * @apiNote Field must be of Type Property and not Null ( as long as
 *          {@link RapidFX#setUp(rapidFX.interfaces.RapidFXComponent...) the
 *          Setup} was called before else it will crash) if it was null
 *          before<br>
 * @implNote During
 *           {@link RapidFX#rapidGenerate(rapidFX.interfaces.RapidController)
 *           the Rapid Generation} all Fields tagged with this Annotation will
 *           get bound to the Field in the Controller which has the same name
 *           and is also a ObjectProperty which is NOT null
 */
@Target(
{ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RapidFXcontroller
{

}
