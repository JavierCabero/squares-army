package appearance;
import entity.Entity;
import entity.EntityColor;


public interface IRenderable extends Entity {

	public void update();
	public EntityColor getColor();
	public float getSize();
	
}
