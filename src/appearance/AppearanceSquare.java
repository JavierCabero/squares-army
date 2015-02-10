package appearance;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
import entity.EntityColor;

public class AppearanceSquare implements IRenderator {

	private IRenderable entity;

	public AppearanceSquare(IRenderable entity) {
		this.entity = entity;
	}

	public void render() {
		/* Get parameters */
		EntityColor color = entity.getColor();
		float x = entity.getX();
		float y = entity.getY();
		float size = entity.getSize() / 2;

		/* Render */
		glColor3f(color.red, color.green, color.blue);

		glBegin(GL_QUADS);
		glVertex2f(x - size, y - size); // Upper-left
		glVertex2f(x + size, y - size); // Upper-right
		glVertex2f(x + size, y + size); // Bottom-right
		glVertex2f(x - size, y + size); // Botton-left
		glEnd();
	}

}
