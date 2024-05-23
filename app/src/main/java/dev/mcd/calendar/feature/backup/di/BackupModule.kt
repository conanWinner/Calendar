package dev.mcd.calendar.feature.backup.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
//import dev.mcd.calendar.feature.backup.data.BackupStoreImpl
import dev.mcd.calendar.feature.backup.data.ExtractBackupFileImpl
import dev.mcd.calendar.feature.backup.data.ImportBackupFileImpl
//import dev.mcd.calendar.feature.backup.domain.BackupStore
//import dev.mcd.calendar.feature.backup.domain.CopyDatabaseToProvider
//import dev.mcd.calendar.feature.backup.domain.ExportDatabase
import dev.mcd.calendar.feature.backup.domain.ExtractBackupFile
import dev.mcd.calendar.feature.backup.domain.ImportBackupFile
import dev.mcd.calendar.di.CalendarDBFolder
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BackupModule {

    //
    // Backup Common
    //

    @Provides
    @BackupFileMimeType
    fun backupFileMimeType() = BACKUP_FILE_MIME_TYPE

    @Provides
    @Singleton
    @ExportFile
    fun exportFile(
        @ApplicationContext context: Context,
    ): File {
        val folder = File(context.filesDir, EXPORT_DIR)
        folder.mkdirs()
        return File(folder, BACKUP_FILE_NAME)
    }


    @Provides
    fun importBackupFile(
        @ApplicationContext
        context: Context,
        @ImportFile
        file: File,
    ): ImportBackupFile = ImportBackupFileImpl(
        context = context,
        importFile = file,
    )

    @Provides
    @Singleton
    @ImportFile
    fun importFile(
        @ApplicationContext context: Context,
    ): File {
        val folder = File(context.filesDir, IMPORT_DIR)
        folder.mkdirs()
        return File(folder, BACKUP_FILE_NAME)
    }

    @Provides
    fun extractBackupFile(
        @ImportFile importFile: File,
        @CalendarDBFolder dbFolder: File,
    ): ExtractBackupFile = ExtractBackupFileImpl(
        importFile = importFile,
        targetDir = dbFolder,
    )

    private companion object {
        const val BACKUP_FILE_MIME_TYPE = "application/zip"
        const val EXPORT_DIR = "export"
        const val IMPORT_DIR = "import"
        const val BACKUP_FILE_NAME = "calendar_backup.zip"
    }
}
