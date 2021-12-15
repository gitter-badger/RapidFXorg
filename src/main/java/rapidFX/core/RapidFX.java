package rapidFX.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import rapidFX.annotation.RautoGenerate;
import rapidFX.annotation.Rcontroller;
import rapidFX.annotation.Rmodel;
import rapidFX.interfaces.RapidController;
import rapidFX.interfaces.RapidFXComponent;
import rapidFX.interfaces.RapidView;

public class RapidFX
{
	private static final Class<RautoGenerate> AUTO_GENERATION_ANNOTATION = RautoGenerate.class;
	private static final Class<Rcontroller> VIEW_TO_CONTROLLER_ANNOTATION = Rcontroller.class;
	private static final Class<Rmodel> VIEW_TO_MODEL_ANNOTATION = Rmodel.class;

	/**
	 * @implNote the Setup and Connect Methods will get Called properly for the
	 *           Controller and the View and the Model so it's not necessary to call
	 *           them {@summary Takes from the Controller the View and the Model and
	 *           RautoGenerates all Attributes + binds the Properties from the View}
	 * @apiNote Watch {@link RapidFX#connect(RapidView, RapidFXComponent, Class)
	 *          connect} and {@link setUp} for more Information
	 * @param controller
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static RapidController rapidGenerate(final RapidController controller)
			throws IllegalArgumentException, IllegalAccessException
	{
		final var model = controller.getModel();
		final var view = controller.getView();

		setUp(model, controller, view);

		connect(view, controller, VIEW_TO_CONTROLLER_ANNOTATION);
		connect(view, model, VIEW_TO_MODEL_ANNOTATION);

		return controller;
	}

	/**
	 * all ObjectProperty which are tagged with RautoGenerate will get the default
	 * value of "new SimpleObjectProperty<>" <br>
	 * Rmodel and Rcontroller are only allowed in Classes which implement the
	 * RapidView the System will stop if this Rule is violated<br>
	 * <br>
	 * Should only be used on Classes which are not affected by the
	 * {@link RapidFX#rapidGenerate(RapidController) rapidGeneration} but still want
	 * the {@link RapidFX#setUp(RapidFXComponent...) auto Generation } for Initializing their Fields
	 * <br>
	 * for Unit Testing this Method can be used only on a Single Object to see if
	 * its fully Functional, this should be the case for the Controller and the Model
	 *
	 * @param toSetUpObjects
	 */
	public static void setUp(RapidFXComponent... toSetUpObjects)
	{
		for (var toSetUp : toSetUpObjects)
		{
			final Field[] fields = toSetUp.getClass().getDeclaredFields();

			for (var field : fields)
			{
				final FieldHandler<?> fieldHandler = new FieldHandler<>(field, toSetUp);

				if (fieldHandler.isAnnotationPresent(AUTO_GENERATION_ANNOTATION))
				{
					fieldHandler.setDefaultValue();
				}
			}
		}
	}

	/**
	 * Connects all fields in the View which are Tagged as Rmodel with the model
	 * Connects all fields in the View which are Tagged as Rcontroller with the
	 * controller
	 *
	 * @apiNote the restriction to connect only Rcontroller or Rmodel to a controller or Model is not
	 *          set. The RapidFXComponent can be here Any RapidFXComponent<br>
	 *          in theory you can tag ObjectProperty Fields in your view with any
	 *          annotation and bind them to any other Class as it's based on
	 *          searching Fields with the given annotation and searching in the
	 *          bindTo Object the Fields with the same Name <br>
	 *          this can be used to test only certain parts by changing the
	 *          Annotations, but in the Real Application RapidFX.rapidGenerate()
	 *          should be used to set the bindings and connecting the view
	 * @param view
	 * @param bindTo
	 * @param annotation
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void connect(final RapidView<?> view, final RapidFXComponent bindTo,
			final Class<? extends Annotation> annotation) throws IllegalArgumentException, IllegalAccessException

	{
		final var viewFields = view.getClass().getDeclaredFields();

		for (Field viewField : viewFields)
		{
			final FieldHandler<?> fieldHandler = new FieldHandler<>(viewField, view);

			if (fieldHandler.isAnnotationPresent(annotation))
			{
				fieldHandler.bindProperties(bindTo);
			}
		}
	}
}