package examples.HelloWorld;

import javafx.beans.property.ObjectProperty;
import rapidFX.annotation.RautoGenerate;
import rapidFX.interfaces.RapidModel;
//"Buisness Logic" -> Everything that only needs Properties and backend to work
// no Eventhandlers, no Nodes here
public class LoginModel implements RapidModel
{
	@RautoGenerate
	private ObjectProperty<String> titleText;
	@RautoGenerate
	private ObjectProperty<String> greetingsProperty;

	@Override
	public void setUpModel()
	{
		
		titleText.set("Hello World");
		
	}
	public void login()
	{
		
		titleText.set("You are logged in");
		System.out.println("Hello from the Model");

	}

}