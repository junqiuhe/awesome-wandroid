package com.jackh.wandroid.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/31 10:26
 * Description:
 */
data class SystemTreeInfo(
    val id: Int,
    val name: String,
    val children: List<SystemTreeInfo>?,
    val courseId: Int,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.createTypedArrayList(CREATOR),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeTypedList(children)
        parcel.writeInt(courseId)
        parcel.writeInt(order)
        parcel.writeInt(parentChapterId)
        parcel.writeByte(if (userControlSetTop) 1 else 0)
        parcel.writeInt(visible)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SystemTreeInfo> {
        override fun createFromParcel(parcel: Parcel): SystemTreeInfo {
            return SystemTreeInfo(parcel)
        }

        override fun newArray(size: Int): Array<SystemTreeInfo?> {
            return arrayOfNulls(size)
        }
    }
}