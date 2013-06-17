package com.android.taylorzero.test.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import com.android.taylorzero.test.opengl.square.FlatColoredSquare;
import com.android.taylorzero.test.opengl.square.SimplePlane;
import com.android.taylorzero.test.opengl.square.SmoothColoredSquare;
import com.android.taylorzero.test.opengl.square.Square;

public class OpenGLRenderer implements Renderer {
	private Square square = null;
	private FlatColoredSquare flatsquare = null;
	private SmoothColoredSquare smoothcolorresquare = null;
	private SimplePlane simpleplane = null;

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | // OpenGL docs.
				GL10.GL_DEPTH_BUFFER_BIT);

		// gl.glLoadIdentity();
		// Draw our square.
		// square.draw(gl);
		// flatsquare.draw(gl);
		// smoothcolorresquare.draw(gl);
		simpleplane.draw(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);// OpenGL docs.
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);// OpenGL docs.
		// Reset the projection matrix
		gl.glLoadIdentity();// OpenGL docs.
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
		// Reset the modelview matrix
		gl.glLoadIdentity();// OpenGL docs.
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // OpenGL docs.
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);// OpenGL docs.
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);// OpenGL docs.
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
				GL10.GL_NICEST);

		// Initialize our square.
		square = new Square();
		flatsquare = new FlatColoredSquare();
		smoothcolorresquare = new SmoothColoredSquare();
		simpleplane = new SimplePlane();
	}

	public void test() {
		// a float is 4 bytes, therefore we multiply the
		// number if vertices with 4.
		// 頂點定義
		ByteBuffer vbb = ByteBuffer
				.allocateDirect(TutorialPartISetting.vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		FloatBuffer vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(TutorialPartISetting.vertices);
		vertexBuffer.position(0);

		// To gain some performance we also put this ones in a byte buffer.
		// short is 2 bytes, therefore we multiply the number if vertices with
		// 2.
		// 正方形定義
		ByteBuffer ibb = ByteBuffer
				.allocateDirect(TutorialPartISetting.indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		ShortBuffer indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(TutorialPartISetting.indices);
		indexBuffer.position(0);

		// Enabled the vertex buffer for writing and to be used during
		// rendering.
		GL10 gl = null;
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer); // OpenGL docs.
		// When you are done with the buffer don't forget to disable it.
		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
		// 设置逆时针方法为面的“前面”
		gl.glFrontFace(GL10.GL_CCW);
		// 打开 忽略“后面”设置：
		gl.glEnable(GL10.GL_CULL_FACE);
		// 明确指明“忽略“哪个面
		gl.glCullFace(GL10.GL_BACK);

	}
}
