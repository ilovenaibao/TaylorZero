package com.android.taylorzero.test.opengl.square;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.format.Time;

public class SmoothColoredSquare extends Square {
	// The colors mapped to the vertices.
	float[] colors = { 1f, 0f, 0f, 1f, // vertex 0 red
			0f, 1f, 0f, 1f, // vertex 1 green
			0f, 0f, 1f, 1f, // vertex 2 blue
			1f, 0f, 1f, 1f, // vertex 3 magenta
	};

	private FloatBuffer colorBuffer;

	public SmoothColoredSquare() {
		// float has 4 bytes, colors (RGBA) * 4 bytes
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		colorBuffer = cbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
		rotate = 0f;
	}

	private float max_rotate = 90;
	private float rotate = 0f;

	@Override
	public void draw(GL10 gl) {
		gl.glLoadIdentity();
		// Enable the color array buffer to be
		// used during rendering.
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// Point out the where the color buffer is.
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		gl.glTranslatef(0, 0, -4);
		gl.glScalef(0.2f, 0.2f, 1f);
		// rotate += 1f;
		// if (max_rotate < rotate) {
		// rotate = 0;
		// }
		rotate = 70;
		gl.glRotatef(rotate, 0.0f, 1.0f, 0.0f);
		super.draw(gl);
	}

}
