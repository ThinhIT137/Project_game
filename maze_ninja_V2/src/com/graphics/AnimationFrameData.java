package com.graphics;

public class AnimationFrameData {
	private int frameStart;
	private int frameEnd;

	public AnimationFrameData(String name, String animation) {
		switch (name) {
		case "player": {
			if (animation == "run") {
				this.frameStart = 4;
				this.frameEnd = 11;
			}
			if (animation == "crouch") {
				this.frameStart = 14;
				this.frameEnd = 16;
			}
			if (animation == "attack") {
				this.frameStart = 15;
				this.frameEnd = 31;
			}
			break;
		}
		case "Demon": {
			if (animation == "idle") {
				this.frameStart = 0;
				this.frameEnd = 7;
			}
			if (animation == "run") {
				this.frameStart = 8;
				this.frameEnd = 15;
			}
			if (animation == "attack") {
				this.frameStart = 16;
				this.frameEnd = 23;
			}
			if (animation == "stun") {
				this.frameStart = 24;
				this.frameEnd = 25;
			}
			if (animation == "jump") {
				this.frameStart = 34;
				this.frameEnd = 41;
			}
			break;
		}
		}
	}

	public int getFrameStart() {
		return frameStart;
	}

	public int getFrameEnd() {
		return frameEnd;
	}
}
