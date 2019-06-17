package com.medilot.demo.mdproject.gallery.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.medilot.demo.mdproject.R
import com.medilot.demo.mdproject.common.data.model.Image


class GalleryAdapter(context: Context, items: ArrayList<Image>) : BaseAdapter() {

    private var mItems: ArrayList<Image>
    private var mInflater: LayoutInflater
    private var context: Context

    init {
        mInflater = LayoutInflater.from(context)
        mItems = items
        this.context = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        val picture: ImageView

        if (view == null) {
            view = mInflater.inflate(R.layout.gallery_item, parent, false)
            view!!.setTag(R.id.picture, view.findViewById(R.id.picture))

        }


        val item = getItem(position) as Image
        picture = view.getTag(R.id.picture) as ImageView

        Glide.with(context)
            .asBitmap()
            .load(item.imageData)
            .placeholder(R.drawable.empty)
            .fitCenter()
            .into(picture)

        return view
    }

    override fun getItem(item: Int): Image? {
        return if (mItems != null)
            mItems[item]
        else
            null
    }

    override fun getItemId(itemId: Int): Long {
        return if (mItems != null)
            itemId.toLong()
        else
            0
    }

    override fun getCount(): Int {
        return if (mItems != null)
            mItems.size
        else
            0
    }
}