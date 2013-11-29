package test.languages.awk;

import org.modelcc.Pattern;
import org.modelcc.Value;
import org.modelcc.Prefix;
import org.modelcc.Suffix;

@Prefix("/")
@Suffix("/")
@Pattern(regExp="[^/]*")
public class AWKRegularExpressionPattern extends AWKPattern 
{
	@Value
	private String regexp;

	
	public String toString ()
	{
		return "/"+regexp+"/";
	}
	
}
