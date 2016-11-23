package com.yanbober.support_library_demo.DataHelpers;

/**
 * Created by cat6572325 on 16-11-21.
 */
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.*;
import android.util.*;
import android.widget.*;
public class DataHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String SWORD="SWORD";
    String name,s;
    //  构造方法：
    public DataHelper(Context context) {
        super(context, "baseName", null,1);
    }
    //	//三个不同参数的构造函数
//	//带全部参数的构造函数，此构造函数必不可少
//	public DataHelper(Context context, String name, CursorFactory factory,
//					  int version) {
//		super(context, name, factory, version);
//
//	}
//	//带两个参数的构造函数，调用的其实是带三个参数的构造函数
//	public DataHelper(Context context,String name){
//		this(context,name,VERSION);
//	}
//	//带三个参数的构造函数，调用的是带所有参数的构造函数
//	public DataHelper(Context context,String name,int version){
//		this(context, name,null,version);
//	}
    //创建数据库
    public void onCreate(SQLiteDatabase db) {
        Log.i(SWORD,"create a Database");
        //创建数据库sql语句
        String sql = "create table Flag(_id" +
                " integer primary key" +
                ",itPhone text" +
                ",itPass text" +
                ",itName text" +
                ",itHeadPictrue text" +
                ",itToken"+
                ",flag text)";
        //执行创建数据库操作
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //创建成功，日志输出提示
        Log.i(SWORD,"update a Database");
    }
    public long inst(SQLiteDatabase db,String name,Context cot)
    {
        ContentValues contentValues=null;
        try {
            //SQLiteDatabase db5 = dbHelper5.getReadableDatabase();
            String[] data = name.split("\\|");
            db = getWritableDatabase();//获取可写SQLiteDatabase对象
            //调用delete方法，删除数据
            db.delete("Flag", "_id=?", new String[]{"1"});
            //ContentValues类似map，存入的是键值对
            contentValues = new ContentValues();
            contentValues.put("itPhone", data[0]);
            contentValues.put("itPass", data[1]);
            contentValues.put("itName", data[2]);
            contentValues.put("itHeadPictrue", data[3]);
            contentValues.put("itToken", data[4]);

            contentValues.put("flag", data[5]);
            //db.execSQL("insert into tb_test(_id,tname,tage,ttle) values(3,'h','h','f')");
        }catch (SQLiteDiskIOException e)
        {

        }finally {
            return db.insert("Flag", null, contentValues);
        }


    }
    public String readData(String name)
    {
        SQLiteDatabase db = getReadableDatabase();
        String s,flag;
        String[] data;
        data=name.split("\\|");
        if(data[0].equals("flag"))
        {
            Cursor cursor = db.rawQuery("select * from Flag", null);
            while (cursor.moveToNext()) {
                name = cursor.getString(1)+"|"; //获取第一列的值,第一列的索引从0开始
                name+= cursor.getString(2)+"|";//获取第二列的值
                name += cursor.getString(3)+"|";//获取第三列的值
                name += cursor.getString(4)+"|";
                name+=cursor.getString(5)+"|";

                name+=cursor.getString(6);

            }
            cursor.close();
            db.close();

        }else{
            //s="insert into user4(id, name) values(3,'g')";
            //db.execSQL(s);

        }
        return name;
    }
    // * 更新记录的，跟插入的很像

    public void update(String _id,String tname, String pass, String flag){
        SQLiteDatabase db= getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("itPhone", tname);
        contentValues.put("itPass", pass);

        contentValues.put("flag", flag);

        db.update("Flag", contentValues,
                "_id=?",
                new String[]{_id});
    }

    //* 查询所有数据
    //   * @return Cursor

    public Cursor select(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(
                "Flag",
                new String[]{"_id","itPone","itPass","itName","flag"},
                null,
                null, null, null, "_id 1");

    }
	/*关于db.query方法的参数，有很多，为了防止大家弄乱，我简单说一下
	 参数1：表名
	 参数2：返回数据包含的列信息，String数组里放的都是列名
	 参数3：相当于sql里的where，sql里where后写的内容放到这就行了，例如：tage>?
	 参数4：如果你在参数3里写了?（知道我为什么写tage>?了吧），那个这里就是代替?的值 接上例：new St
	 那个这里就是代替?的值 接上例：new String[]{"30"}
	 参数5：分组，不解释了，不想分组就传null
	 参数6：having，想不起来的看看SQL
	 参数7：orderBy排序

	 到这里，你已经完成了代码最多的第一步！我们来看看都用到了那些类：
	 SQLiteOpenHelper我们继承使用的
	 SQLiteDatabase增删查改都离不开它，即使你直接用sql语句，也要用到execSQL(sql)

	 二、这里无非是对DatabaseHelper类定义方法的调用，没什么可说的，不过我还是对查询再唠叨几句吧
	 Android查询出来的结果一Cursor形式返回
	 cursor = sqLiteHelper.select();//是不是很简单？
	 查询出来的cursor一般会显示在listView中，这就要用到刚才提到的SimpleCursorAdapter
	 SimpleCursorAdapter adapter = new SimpleCursorAdapter(
	 this,
	 R.layout.list_row,
	 cursor,
	 new String[]{"tname","ttel"},
	 new int[]{R.id.TextView01,R.id.TextView02}
	 );
	 */



}