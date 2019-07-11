package task.examination.com.examinationtask.sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import task.examination.com.examinationtask.R;

public class ShopDatabase extends SQLiteAssetHelper
{
    private static final String TAG = "ShopDatabase";

    private static final String DATABASE_NAME = "shop.db";
    private static final int DATABASE_VERSION = 1;
    public final String DATABASE_PATH;

    public SQLiteDatabase DATABASE;
    private final Context DATABASE_Context;

    public ShopDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DATABASE_Context = context;
        DATABASE_PATH = context.getResources().getString(R.string.database_path);

        if (checkDataBase())
        {
            openDataBase();
        }
        else
        {
            try
            {
                this.getReadableDatabase();
                copyDataBase();
                this.close();
                openDataBase();
            }
            catch (IOException ex)
            {
                Log.e(TAG, "Ошибка при копировании " + Log.getStackTraceString(ex));
            }
        }
    }

    public void createDataBase() throws IOException
    {
        boolean dbExist = checkDataBase();

        if (!dbExist)
        {
            this.getReadableDatabase();
            this.copyDataBase();
        }
    }

    private void copyDataBase() throws IOException
    {
        InputStream myInput = DATABASE_Context.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0)
        {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException
    {
        String dbPath = DATABASE_PATH + DATABASE_NAME;
        DATABASE = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;
        boolean exist = false;
        try
        {
            String dbPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(dbPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException ex)
        {
            Log.e(TAG, "База данных не существует " + Log.getStackTraceString(ex));
        }

        if (checkDB != null)
        {
            exist = true;
            checkDB.close();
        }
        return exist;
    }

    @Override
    public synchronized void close()
    {
        if(DATABASE != null)
            DATABASE.close();

        super.close();
    }
}
