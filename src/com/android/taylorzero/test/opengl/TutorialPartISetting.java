package com.android.taylorzero.test.opengl;

import java.nio.ByteBuffer;

public class TutorialPartISetting {
	public static float vertices[] = { -1.0f, 1.0f, 0.0f, // 0, Top Left
			-1.0f, -1.0f, 0.0f, // 1, Bottom Left
			1.0f, -1.0f, 0.0f, // 2, Bottom Right
			1.0f, 1.0f, 0.0f, // 3, Top Right
	};

	public static short[] indices = { 0, 1, 2, 0, 2, 3 };

}
