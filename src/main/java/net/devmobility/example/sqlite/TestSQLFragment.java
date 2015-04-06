package net.devmobility.example.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.devmobility.example.R;

public class TestSQLFragment extends Fragment {

        EditText statementBox;
        TextView resultText;
        SQLiteDatabase wDatabase;
        SQLiteDatabase rDatabase;

        public TestSQLFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_testsql,  container, false);
            statementBox = (EditText)rootView.findViewById(R.id.statement_edit);
            statementBox.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                public void onFocusChange(View v, boolean hasFocus){
                    if (hasFocus) {
                        ((EditText) v).selectAll();
                    }
                }
            });
            View resultScroll = rootView.findViewById(R.id.result_scroll);
            resultText = (TextView) resultScroll.findViewById(R.id.result_text);
            View executeButton = rootView.findViewById(R.id.execute_button);
            executeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executeSQL();
                }
            });
            return rootView;
        }

        private void executeSQL() {
            resultText.setText("");
            String statement = statementBox.getText().toString();
            if (!TextUtils.isEmpty(statement)) {
                if (statement.toLowerCase().startsWith("select")) {
                    executeSelect(statement);
                } else if (statement.toLowerCase().startsWith("pragma")) {
                    executePragma(statement);
                } else {
                    execute(statement);
                }
            } else {
                showMessage("No statement entered!");
            }
        }

        private void executeSelect(String statement) {
            Cursor cursor = rDatabase.rawQuery(statement, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    resultText.append("Results:");
                    do {
                        resultText.append("\n");
                        for (int i = 0; i < cursor.getColumnCount(); i++) {
                            if (i > 0) {
                                resultText.append(",");
                            }
                            resultText.append(cursor.getColumnNames()[i]);
                            resultText.append(" = ");
                            switch (cursor.getType(i)) {
                                case Cursor.FIELD_TYPE_BLOB:
                                    resultText.append("Blob - bytes = " + cursor.getBlob(i));
                                    break;
                                case Cursor.FIELD_TYPE_FLOAT:
                                    float resultFloat = cursor.getFloat(i);
                                    resultText.append(Float.toString(resultFloat));
                                    break;
                                case Cursor.FIELD_TYPE_INTEGER:
                                    int resultInt = cursor.getInt(i);
                                    resultText.append(Integer.toString(resultInt));
                                    break;
                                case Cursor.FIELD_TYPE_STRING:
                                    String resultStr = cursor.getString(i);
                                    resultText.append(resultStr);
                                    break;
                            }
                        }
                    } while (cursor.moveToNext());
                } else {
                    showMessage("No results!");
                }
            } catch (SQLiteException e) {
                showMessage("Select failed - " + e.getMessage());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        private void executePragma(String statement) {
            Cursor cursor = rDatabase.rawQuery(statement, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        resultText.append("name: " + cursor.getString(1) + " type: " + cursor.getString(2) + "\n");
                    } while (cursor.moveToNext());
                }
            } catch (SQLiteException e) {
                showMessage("Pragma failed - " + e.getMessage());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        private void execute(String statement) {
            wDatabase.beginTransaction();
            try {
                wDatabase.execSQL(statement);
                wDatabase.setTransactionSuccessful();
            } catch (SQLiteException e) {
                showMessage("Exec failed - " + e.getMessage());
            } finally {
                wDatabase.endTransaction();
                showMessage("Transaction finished!");
            }

        }

        private void showMessage(String message) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStart() {
            super.onStart();
            TestSQLiteOpenHelper helper = new TestSQLiteOpenHelper(getActivity());
            wDatabase = helper.getWritableDatabase();
            rDatabase = helper.getReadableDatabase();
        }

        @Override
        public void onStop() {
            if (wDatabase != null) {
                wDatabase.close();
            }
            if (rDatabase != null) {
                rDatabase.close();
            }
            super.onStop();
        }

        private static class TestSQLiteOpenHelper extends SQLiteOpenHelper {

            public TestSQLiteOpenHelper(Context context) {
                super(context, "TestDB", null, 1);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                // Create some tables, etc.
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int i, int i2) {
                // Update tables, etc.
            }
        }
}
