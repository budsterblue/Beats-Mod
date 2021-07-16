package com.github.budsterblue.beats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

public class MenuFileArrayAdapter extends ArrayAdapter<MenuFileItem>{

	private final Context c;
	private final int id;
	private final ArrayList<MenuFileItem> items;
	private final boolean showSongBanners = Tools.getBooleanSetting(R.string.showSongBanners, R.string.showSongBannersDefault);
	
	public MenuFileArrayAdapter(Context context, int textViewResourceId, ArrayList<MenuFileItem> items) {
		super(context, textViewResourceId, items);
		this.c = context;
		this.id = textViewResourceId;
		this.items = items;
	}
	
	public MenuFileItem getItem (int i) {
		return items.get(i); 
	} 
	
	@androidx.annotation.NonNull
	public View getView(int position, View convertView, @NonNull ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			 LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 v = vi.inflate(id, null);			 
		}
		final MenuFileItem i = items.get(position);
		if (i != null) {
			ImageView iv = v.findViewById(R.id.iconview);
			if (iv != null) {
				String s = i.getName();

				if (i.getFile() == null) {
					iv.setImageResource(R.drawable.ic_arrow_back_filled_black);
				} else if (i.isDirectory()) {
					if (Tools.checkStepfileDir(i.getFile()) != null) {

						//get image (why is this so complicated?)
						if (showSongBanners) {
							String bannerpath = null;
							File f = new File(i.getPath());
							if (f.isDirectory()) {
								File[] files = f.listFiles();
								if (files != null) {
									for (File fi : files) {
										String fs = fi.getPath();
										if (fs.contains(".sm")) {
											String bannerbg;
											FileInputStream is;
											BufferedReader reader;
											final File smfile = new File(fs);

											if (smfile.exists()) {
												try {
													is = new FileInputStream(smfile);
													reader = new BufferedReader(new InputStreamReader(is));
													String line;
													try {
														line = reader.readLine();
														while (line != null) {
															if (line.contains("#BANNER")) {
																bannerbg = line.replace("#BANNER:", "").replace(";", "").replace("../", "");
																bannerpath = f + "/" + bannerbg;
																break;
															}
															line = reader.readLine();
														}
													} catch (IOException e) {
														e.printStackTrace();
													}
												} catch (FileNotFoundException e) {
													e.printStackTrace();
												}
											}
										}
									}
								}

							}

							if (bannerpath != null) {
								File imgFile = new File(bannerpath);
								if (imgFile.exists()) {
									iv.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
								} else {
									iv.setImageResource(R.drawable.icon_small);
								}
							} else {
								iv.setImageResource(R.drawable.icon_small);
							}
						} else {
							iv.setImageResource(R.drawable.icon_small);
						}
					} else {
						iv.setImageResource(R.drawable.ic_folder_filled_black);
					}
				} else if (Tools.isStepfile(i.getPath())) {
					if (Tools.isSMFile(s)) {
						iv.setImageResource(R.drawable.icon_sm);
					} else if (Tools.isDWIFile(s)) {
						iv.setImageResource(R.drawable.icon_dwi);
					} else {
						iv.setImageResource(R.drawable.icon_warning);
					}
				} else if (Tools.isStepfilePack(s)) {
					iv.setImageResource(R.drawable.ic_folder_zip_filled_black);
				} else if (Tools.isLink(s)) {
					iv.setImageResource(R.drawable.ic_link_filled_black);
				} else if (Tools.isText(s)) {
					iv.setImageResource(R.drawable.ic_text_snippet_filled_black);
				} else {
					iv.setImageResource(R.drawable.icon_warning);
				}
			}
			TextView tv = v.findViewById(R.id.textview);
			if (tv != null) {
				tv.setText(i.getName());
				if (!showSongBanners){
					tv.setBackgroundColor(Color.TRANSPARENT);
				}
			}
		}
		return v;
	}

}
