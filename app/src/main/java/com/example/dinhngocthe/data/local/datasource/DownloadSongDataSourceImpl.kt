package com.example.dinhngocthe.data.local.datasource

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.example.dinhngocthe.data.local.entities.Song
import com.example.dinhngocthe.data.remote.model.SongDto
import com.example.dinhngocthe.data.remote.model.toSong
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadSongDataSourceImpl() : DownloadSongDataSource {
    override suspend fun downloadAndSaveSongDtosToStorage(context: Context, songDtos: List<SongDto>): List<Song> {
        val songs = coroutineScope {
            songDtos.map { songDto ->
                async(Dispatchers.IO) {
                    val file = downloadMp3FileAndSave(context, songDto.path, songDto.songName)
                    file?.let {
                        val coverArtUri = getEmbeddedCoverUriFromMp3(it, context)
                        songDto.toSong(Uri.fromFile(it), coverArtUri)
                    }
                }
            }.awaitAll().filterNotNull()
        }
        return songs
    }

    private suspend fun getEmbeddedCoverUriFromMp3(file: File, context: Context): Uri? {
        val uri = withContext(Dispatchers.IO) {
            try {
                val mmr = MediaMetadataRetriever()
                mmr.setDataSource(file.absolutePath)

                mmr.embeddedPicture?.let { bytes ->
                    val coverFile =
                        File(context.cacheDir, "${file.nameWithoutExtension}_cover.jpg")
                    coverFile.writeBytes(bytes)
                    Uri.fromFile(coverFile)
                }.also {
                    mmr.release()
                }
            } catch (_: Exception) {
                null
            }
        }
        return uri
    }

    private suspend fun downloadMp3FileAndSave(context: Context, url: String, fileName: String): File? {
        val file = withContext(Dispatchers.IO) {
            val directory = File(context.filesDir, "songs")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, fileName)
            if (file.exists()) {
                return@withContext null
            }

            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.connect()

                if (connection.responseCode != HttpURLConnection.HTTP_OK) return@withContext null

                val inputStream = connection.inputStream
                val outputStream = FileOutputStream(file)

                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }

                return@withContext file
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }

        return file
    }
}
