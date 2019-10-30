package com.jackh.wandroid.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/29 16:50
 * Description:
 */

/**
{
    "desc": "Android高级进阶直播课免费学习",
    "id": 23,
    "imagePath": "https://wanandroid.com/blogimgs/67c28e8c-2716-4b78-95d3-22cbde65d924.jpeg",
    "isVisible": 1,
    "order": 0,
    "title": "Android高级进阶直播课免费学习",
    "type": 0,
    "url": "https://url.163.com/4bj"
}
*/

data class BannerInfo(
    val id: Int,
    val title: String,
    val desc: String,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val type: Int,
    val url: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeString(imagePath)
        parcel.writeInt(isVisible)
        parcel.writeInt(order)
        parcel.writeInt(type)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BannerInfo> {
        override fun createFromParcel(parcel: Parcel): BannerInfo {
            return BannerInfo(parcel)
        }

        override fun newArray(size: Int): Array<BannerInfo?> {
            return arrayOfNulls(size)
        }
    }

}