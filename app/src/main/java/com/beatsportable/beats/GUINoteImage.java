package com.beatsportable.beats;

//import android.graphics.Color;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

public class GUINoteImage {
	
	/*
# print "\t\tswitch (_measurefraction) {"
# fracs = [4,8,12,16,24,32,48,64,192]
# frac_cases = [(d, "case %d" % d) for d in fracs] + [(None, "default")]
# for frac, frac_case in frac_cases:
# 	print "\t\t" + frac_case + ":"
# 	print "\t\t\tswitch (_pitch) {"
# 	for n, pitch_case in enumerate(("left","down","up","right")):
# 		print "\t\t\tcase %d:" % n,
# 		if frac_case == "default":
# 			print "return _clicked ? "/step/arrow_%s_hit :" % pitch_case
# 			print "\t\t\t\t"/step/arrow_%s_control;" % pitch_case
# 		else:
# 			print "return "/step/arrow_%s_%s;" % (pitch_case, frac)
# 	print "\t\t\t}" 
# print "\t\t}"
	 */
	// For osu! Mod
	// Lets just hardcode this into GUIFallingOsuFading since its only 2 anyway
	//public static int rsrc(boolean missed) {
		//return missed ? "/osu/osu_beat_miss : "/osu/osu_beat_hit;
	//}
	public static final int OSU_FRACTION_MAX = 4;
	/*public static String osu_beat(int _measurefraction) {
		switch (_measurefraction) {
			default:
			case 1: return "/osu/osu_beat_1.png";
			case 2: return "/osu/osu_beat_2.png";
			case 3: return "/osu/osu_beat_3.png";
			case 4: return "/osu/osu_beat_4.png";
		}
	}
	
	public static int osu_circle(int _measurefraction) {
		switch(_measurefraction) {
			case 1: return Color.BLUE;
			case 2: return Color.RED;
			case 3: return Color.YELLOW;
			case 4: return Color.GREEN;
			default: return Color.WHITE; // Something is wrong
		}
	}*/

	private LruCache<String, Bitmap> memoryCache;
	public GUINoteImage(){
		// Get max available VM memory, exceeding this amount will throw an
		// OutOfMemory exception. Stored in kilobytes as LruCache takes an
		// int in its constructor.
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = maxMemory / 8;

		memoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in kilobytes rather than
				// number of items.
				return bitmap.getByteCount() / 1024;
			}
		};
	}

	public void addBitmapToMemoryCache(int pitch_to_display, int fraction, boolean _clicked) {
		String key = String.valueOf(pitch_to_display + fraction);
		if (getBitmapFromMemCache(key) == null) {
			memoryCache.put(key, getBitmapReloaded(rsrc(pitch_to_display, fraction, _clicked), Tools.button_w, Tools.button_h));
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		return memoryCache.get(key);
	}


	public Bitmap getBitmapReloaded(String rsrc, int width, int height){
		String path = Tools.getNoteSkinsDir() + rsrc;
		Bitmap loaded = BitmapFactory.decodeFile(path);
		return Bitmap.createScaledBitmap(loaded, width, height, true);
	}

	public static String rsrc(int _pitch, int _measurefraction, boolean _clicked) {
		switch (_measurefraction) {
		case 4:
			switch (_pitch) {
			case 0: return "/step/arrow_left_4.png";
			case 1: return "/step/arrow_down_4.png";
			case 2: return "/step/arrow_up_4.png";
			case 3: return "/step/arrow_right_4.png";
			}
		case 8:
			switch (_pitch) {
			case 0: return "/step/arrow_left_8.png";
			case 1: return "/step/arrow_down_8.png";
			case 2: return "/step/arrow_up_8.png";
			case 3: return "/step/arrow_right_8.png";
			}
		case 12:
			switch (_pitch) {
			case 0: return "/step/arrow_left_12.png";
			case 1: return "/step/arrow_down_12.png";
			case 2: return "/step/arrow_up_12.png";
			case 3: return "/step/arrow_right_12.png";
			}
		case 16:
			switch (_pitch) {
			case 0: return "/step/arrow_left_16.png";
			case 1: return "/step/arrow_down_16.png";
			case 2: return "/step/arrow_up_16.png";
			case 3: return "/step/arrow_right_16.png";
			}
		case 24:
			switch (_pitch) {
			case 0: return "/step/arrow_left_24.png";
			case 1: return "/step/arrow_down_24.png";
			case 2: return "/step/arrow_up_24.png";
			case 3: return "/step/arrow_right_24.png";
			}
		case 32:
			switch (_pitch) {
			case 0: return "/step/arrow_left_32.png";
			case 1: return "/step/arrow_down_32.png";
			case 2: return "/step/arrow_up_32.png";
			case 3: return "/step/arrow_right_32.png";
			}
		case 48:
			switch (_pitch) {
			case 0: return "/step/arrow_left_48.png";
			case 1: return "/step/arrow_down_48.png";
			case 2: return "/step/arrow_up_48.png";
			case 3: return "/step/arrow_right_48.png";
			}
		case 64:
			switch (_pitch) {
			case 0: return "/step/arrow_left_64.png";
			case 1: return "/step/arrow_down_64.png";
			case 2: return "/step/arrow_up_64.png";
			case 3: return "/step/arrow_right_64.png";
			}
		case 192:
			switch (_pitch) {
			case 0: return "/step/arrow_left_192.png";
			case 1: return "/step/arrow_down_192.png";
			case 2: return "/step/arrow_up_192.png";
			case 3: return "/step/arrow_right_192.png";
			}
		default:
			switch (_pitch) {
			case 0: return _clicked ? "/step/arrow_left_hit.png" :
				"/step/arrow_left_control.png";
			case 1: return _clicked ? "/step/arrow_down_hit.png" :
				"/step/arrow_down_control.png";
			case 2: return _clicked ? "/step/arrow_up_hit.png" :
				"/step/arrow_up_control.png";
			case 3: return _clicked ? "/step/arrow_right_hit.png" :
				"/step/arrow_right_control.png";
			}
		}
		return null;
	}
	
}
