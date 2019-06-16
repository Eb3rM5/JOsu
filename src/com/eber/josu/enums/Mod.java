package com.eber.josu.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Mod {

	NoFail(0),
	Easy(1),
	TouchDevice(2),
	Hidden(3),
	HardRock(4),
	SuddenDeath(5),
	DoubleTime(6),
	Relax(7),
	HalfTime(8),
	Nightcore(9),
	Flashlight(10),
	Autoplay(11),
	SpunOut(12),
	Autopilot(13),
	Perfect(14),
	Key4(15),
	Key5(16),
	Key6(17),
	Key7(18),
	Key8(19),
	FadeIn(20),
	Random(21),
	Cinema(22),
	TargetPractice(23),
	Key9(24),
	Coop(25),
	Key1(26),
	Key3(27),
	Key2(28),
	ScoreV2(29),
	LastMod(30);
	
	private int id;
	
	private Mod(int id) {
		this.id = id;
	}
	
	public static Mod valueOf(int offset){
		return offset >= 0 && offset <=28 ? Mod.values()[offset] : null;
	}
	
	public static long bitwiseValue(List<Mod> mods) {
		if (mods != null && mods.size() > 0) {	
			long bitwise = 0;
			for (Mod mod : mods) bitwise += (1 << mod.id);
			return bitwise;
		}
		
		return 0;
	}
	
	public static long bitwiseValue(Mod ... mods) {
		if (mods != null && mods.length > 0) {	
			long bitwise = 0;
			for (Mod mod : mods) bitwise += (1 << mod.id);
			return bitwise;
		}
		
		return 0;
	}
	
	public static List<Mod> modCombination(long bitwiseValue){

		if (bitwiseValue > 0) {
			
			List<Mod> mods = new ArrayList<>();
			
			Mod[] values = values();
			for (int i = 0; i < values.length; i++) {
				long bit = (bitwiseValue >> i);
				
				if (bit == 0) break;
				else if ((bit & 1) == 0) continue;
				
				mods.add(values[i]);
			}
			
			return mods;
			
		} else if (bitwiseValue == 0) return Collections.emptyList();

		return null;
		
	}
	
}