package com.android.taylorzero.setting;

public class TaylorZeroOpeningSetting {
	public static final int MSG_PLAY_OPENING_END = 0x0001;
	public static final int MSG_START_NEW_LAYOUT_ANIMATION = 0x0002;

	public static final String mp4_path = TaylorZeroSetting.Zero_Res_Path
			+ "mp4/";
	public static final String opening_mp4_path = mp4_path + ".zero_opening";
	public static final String preface_mp4_path = mp4_path + ".zero_preface";
	public static final String mp3_path = TaylorZeroSetting.Zero_Res_Path
			+ "Sound/";
	public static final String opening_mp3_bg_path = mp3_path
			+ "outer/Zero_start_ui_bg.mp3"; // .Zero_start_ui_bg
	public static final String touch_start_ui_activity_mp3_path = mp3_path
			+ "inter/chos_ui_activity_touch.mp3";
	public static final String click_start_ui_activity_mp3_path = mp3_path
			+ "inter/chos_ui_activity_click_new.mp3";

	public static final String[] preface_mp4_caption = { "终结的世界，初始的世界。",
			"世界处在虚无之时，存在着一个世界。", "人繁衍，人相残的世界，有人认为那是不完整的因缘。",
			"有人认为如果因缘延续，人类就能永远和平共存下去。",

			"生命，是永恒流淌的水源。", "能够自由连接生命体，能使转世的人使用相同的力量。",
			"在时空尽头飘荡的人，转世的人民是这样尊称的，女神。", "被选中的人，女神赐予他们力量，使他们留下前世的回忆。",
			"他们继承了因缘平静地生活着，但他们也不是万能的。", "相爱的人彼此间的再会充满了快乐，但同时与元素之力的共存留下了庞杂的祸根。",
			"人们开始诅咒那个力量，有的人舍弃了那个力量，消除了那个记忆。",

			"世界改变了七次，诞生新世界之时，没人记得那个力量。", "世界还没有生机的时候存在着一个世界。",
			"那个世界存在相爱的人可以再会的力量。", "那个力量叫源头。", };
}
