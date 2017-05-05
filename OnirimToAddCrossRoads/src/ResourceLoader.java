import java.awt.Image;
import java.awt.Toolkit;

public class ResourceLoader 
{
	static ResourceLoader rL = new ResourceLoader();
	
	public static Image getImage(String fileName)
	{
		return Toolkit.getDefaultToolkit().getImage(rL.getClass().getResource("/"+fileName));
	}
}
