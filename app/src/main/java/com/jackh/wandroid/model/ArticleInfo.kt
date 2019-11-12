package com.jackh.wandroid.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.jackh.wandroid.database.TagConverters

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/29 16:26
 * Description:
 */

/**
{
"apkLink": "",
"audit": 1,
"author": "互联网侦察",
"chapterId": 421,
"chapterName": "互联网侦察",
"collect": false,
"courseId": 13,
"desc": "",
"envelopePic": "",
"fresh": false,
"id": 9940,
"link": "https://mp.weixin.qq.com/s/G5Syqq1Ofxp4VFwEJOHSbg",
"niceDate": "2019-10-24",
"niceShareDate": "2019-10-25",
"origin": "",
"prefix": "",
"projectLink": "",
"publishTime": 1571846400000,
"selfVisible": 0,
"shareDate": 1572008753000,
"shareUser": "",
"superChapterId": 408,
"superChapterName": "公众号",
"tags": [
{
"name": "公众号",
"url": "/wxarticle/list/421/1"
}
],
"title": "阿里巴巴的技术专家，是如何画好架构图的？",
"type": 0,
"userId": -1,
"visible": 1,
"zan": 0
}
 **/
@TypeConverters(TagConverters::class)
data class ArticleInfo(

    @ColumnInfo(name = "article_id")
    val id: Int,

    val apkLink: String,
    val audit: Int,
    val author: String,  //作者
    val chapterId: Int,
    val chapterName: String,  //当前类别
    var collect: Boolean,  //是否收藏
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,   //是否是最新
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,  //分享人
    val superChapterId: Int,
    val superChapterName: String,  //父分类

    @ColumnInfo(name = "tags")
    val tags: List<TagInfo>?,   //tag: 如公众号、项目、问答

    val title: String,  //title.
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int,

    var isTop: Boolean = false//是否置顶

) : IItem, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.createTypedArrayList(TagInfo),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(apkLink)
        parcel.writeInt(audit)
        parcel.writeString(author)
        parcel.writeInt(chapterId)
        parcel.writeString(chapterName)
        parcel.writeByte(if (collect) 1 else 0)
        parcel.writeInt(courseId)
        parcel.writeString(desc)
        parcel.writeString(envelopePic)
        parcel.writeByte(if (fresh) 1 else 0)
        parcel.writeString(link)
        parcel.writeString(niceDate)
        parcel.writeString(niceShareDate)
        parcel.writeString(origin)
        parcel.writeString(prefix)
        parcel.writeString(projectLink)
        parcel.writeLong(publishTime)
        parcel.writeInt(selfVisible)
        parcel.writeLong(shareDate)
        parcel.writeString(shareUser)
        parcel.writeInt(superChapterId)
        parcel.writeString(superChapterName)
        parcel.writeTypedList(tags)
        parcel.writeString(title)
        parcel.writeInt(type)
        parcel.writeInt(userId)
        parcel.writeInt(visible)
        parcel.writeInt(zan)
        parcel.writeByte(if (isTop) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleInfo> {
        override fun createFromParcel(parcel: Parcel): ArticleInfo {
            return ArticleInfo(parcel)
        }

        override fun newArray(size: Int): Array<ArticleInfo?> {
            return arrayOfNulls(size)
        }
    }

}

data class TagInfo(

    val name: String,

    val url: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TagInfo> {
        override fun createFromParcel(parcel: Parcel): TagInfo {
            return TagInfo(parcel)
        }

        override fun newArray(size: Int): Array<TagInfo?> {
            return arrayOfNulls(size)
        }
    }
}