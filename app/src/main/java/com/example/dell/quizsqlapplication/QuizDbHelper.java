package com.example.dell.quizsqlapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.dell.quizsqlapplication.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyAwsomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " +
                 QuestionTable.TABLE_NAME + " ( " +
                 QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 QuestionTable.COLUMN_QUESTION + " TEXT, "+
                 QuestionTable.COLUMN_OPTION1 + " TEXT, "+
                 QuestionTable.COLUMN_option2 + " TEXT, "+
                 QuestionTable.COLUMN_option3 + " TEXT, "+
                 QuestionTable.COLUMN_ANSWER_NR + " INTEGER" +
                 ")";

        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillQuestionTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
    onCreate(db);
    }

    private void fillQuestionTable(){
        Question q1 = new Question("A is correct","A","B","C",1);
        addQuestion(q1);

        Question q2 = new Question("B is correct","A","B","C",2);
        addQuestion(q2);

        Question q3 = new Question("C is correct","A","B","C",3);
        addQuestion(q3);

        Question q4 = new Question("A is correct again","A","B","C",1);
        addQuestion(q4);

        Question q5 = new Question("B is correct again","A","B","C",2);
        addQuestion(q5);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuestionTable.COLUMN_option2,question.getOption2());
        cv.put(QuestionTable.COLUMN_option3,question.getOption3());
        cv.put(QuestionTable.COLUMN_ANSWER_NR,question.getAnswerNr());

        db.insert(QuestionTable.TABLE_NAME,null,cv);
    }

    public List<Question> getAllQuestion() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_option2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_option3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));

                questionList.add(question);

            } while (c.moveToNext());
        }
            c.close();
            return questionList;

    }
}
