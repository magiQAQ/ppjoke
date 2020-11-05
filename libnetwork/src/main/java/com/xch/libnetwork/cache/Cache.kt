package com.xch.libnetwork.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cache")
class Cache(@PrimaryKey val key: String = "", val data: ByteArray? = null) : Serializable