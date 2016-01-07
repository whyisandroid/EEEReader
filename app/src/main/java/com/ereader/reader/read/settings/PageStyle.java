package com.ereader.reader.read.settings;

import com.ereader.client.R;
import com.ereader.reader.read.effect.LayoverEffect;
import com.ereader.reader.read.effect.NoneEffect;
import com.ereader.reader.read.effect.PageSwitchEffect;
import com.ereader.reader.read.effect.RippleEffect;
import com.ereader.reader.read.effect.SimulationEffect;
import com.ereader.reader.read.effect.SlideEffect;
import com.ereader.reader.read.effect.VerticalBlindsEffect;

import java.util.ArrayList;
import java.util.List;

public class PageStyle {

	private final static List<PageStyle> values = new ArrayList<PageStyle>();
	
	public final static PageStyle NONE = new PageStyle("NONE", R.string.page_style_name_none, NoneEffect.class);
	
	public final static PageStyle SIMULATION = new PageStyle("SIMULATION", R.string.page_style_name_simulation, SimulationEffect.class);
	
	public final static PageStyle LAYOVER = new PageStyle("LAYOVER", R.string.page_style_name_layover, LayoverEffect.class);
	
	public final static PageStyle SLIDE = new PageStyle("SLIDE", R.string.page_style_name_slide, SlideEffect.class);
	
	public final static PageStyle RIPPLE = new PageStyle("RIPPLE", R.string.page_style_name_ripple, RippleEffect.class);
	
	public final static PageStyle VERTICAL_BLINDS = new PageStyle("VERTICAL_BLINDS", R.string.page_style_name_vertical_blinds, VerticalBlindsEffect.class);
	
	public final String key;
	public final int name;
	public final Class<? extends PageSwitchEffect> target;
	
	private PageStyle(String key, int name, Class<? extends PageSwitchEffect> target) {
		this.key = key;
		this.name = name;
		this.target = target;
		values.add(this);
	}
	
	public static PageStyle valueOf(String key) {
		for (PageStyle style : values) {
			if (style.key.equals(key)) {
				return style;
			}
		}
		return null;
	}
	
	public static List<PageStyle> values() {
		return values;
	}
	
}
