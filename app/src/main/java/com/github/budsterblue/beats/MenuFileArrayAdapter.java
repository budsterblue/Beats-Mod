package com.github.budsterblue.beats;

import androidx.annotation.NonNull;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MenuFileArrayAdapter extends RecyclerView.Adapter<MenuFileArrayAdapter.ViewHolder> {

    private final ArrayList<MenuFileItem> mItems;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemLongClickListener mLongClickListener;
    private final boolean showSongBanners = Tools.getBooleanSetting(R.string.showSongBanners, R.string.showSongBannersDefault);

    // data is passed into the constructor
    MenuFileArrayAdapter(Context context, int textViewResourceId, ArrayList<MenuFileItem> items) {
        this.mInflater = LayoutInflater.from(context);
        this.mItems = items;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.choose_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MenuFileItem i = mItems.get(position);
        if (i != null) {
            ImageView iv = holder.itemView.findViewById(R.id.iconview);
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
            TextView tv = holder.itemView.findViewById(R.id.textview);
            if (tv != null) {
                tv.setText(i.getName());
                tv.setBackgroundColor(Color.TRANSPARENT);
                if (!showSongBanners){
                    tv.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ViewHolder(View itemView) {
            super(itemView);

            //Long Press
            itemView.setOnLongClickListener(this);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) mLongClickListener.onItemLongClick(view, getAdapterPosition());
            return true;
        }
    }

    // convenience method for getting data at click position
    public MenuFileItem getItem (int i) {
        return mItems.get(i);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // allows clicks events to be caught
    void setLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.mLongClickListener = itemLongClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

}
