package com.example.dinhngocthe.data.local.datasource

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.example.dinhngocthe.data.local.entities.Song
import com.example.dinhngocthe.data.remote.model.SongDto
import com.example.dinhngocthe.data.remote.model.toSong
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadSongDataSourceImpl() : DownloadSongDataSource {
    override suspend fun downloadAndSaveSongDtosToStorage(context: Context, songDtos: List<SongDto>): List<Song> = coroutineScope {
        songDtos.map { songDto ->
            async {
                val file = downloadMp3FileAndSave(context, songDto.path, songDto.songName)
                file?.let {
                    val coverArtUri = getEmbeddedCoverUriFromMp3(file, context)
                    songDto.toSong(Uri.fromFile(it), coverArtUri)
                }
            }
        }.awaitAll().filterNotNull()
    }

    private fun getEmbeddedCoverUriFromMp3(file: File, context: Context): Uri? {
        return try {
            val mmr = MediaMetadataRetriever()
            mmr.setDataSource(file.absolutePath)

            mmr.embeddedPicture?.let { bytes ->
                val coverFile = File(context.cacheDir, "${file.nameWithoutExtension}_cover.jpg")
                coverFile.writeBytes(bytes) // Ghi file ảnh
                Uri.fromFile(coverFile)     // Trả về Uri để hiển thị
            }.also {
                mmr.release()
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun downloadMp3FileAndSave(context: Context, url: String, fileName: String): File? {
        val directory = File(context.filesDir, "songs")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, fileName)
        if (file.exists()) {
            return null // room exists
        }

        return try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) return null

            val inputStream = connection.inputStream
            val outputStream = FileOutputStream(file)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}