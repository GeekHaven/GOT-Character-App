package com.example.gotcharacterapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
    public MyDbHandler(@Nullable Context context) {
        super(context, Params.DATABASE_NAME, null, Params.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String GotQuery = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY, " + Params.KEY_CHARACTER_NAME
                + " TEXT, " + Params.KEY_IMAGE_URL + " TEXT, " + Params.KEY_HOUSE + " TEXT, " + Params.KEY_PEOPLE_KILLED + " TEXT, " + Params.KEY_KILLED_BY + " TEXT, " +
                Params.KEY_PARENTS + " TEXT, " + Params.KEY_SIBLINGS + " TEXT, " + Params.KEY_SPOUSE + " TEXT, " + Params.KEY_CHILDREN + " TEXT, " + Params.KEY_FAVOURITE + " TEXT" + ")";

        sqLiteDatabase.execSQL(GotQuery);
    }

    public void addCharacterItem(CharacterItem characterItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_CHARACTER_NAME, characterItem.getName());
        values.put(Params.KEY_IMAGE_URL, characterItem.getImage_url());
        values.put(Params.KEY_HOUSE, characterItem.getHouse());
        if (characterItem.getPeople_killed() != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("people_killed", new JSONArray(characterItem.getPeople_killed()));
                String peopleKilled = jsonObject.toString();
                values.put(Params.KEY_PEOPLE_KILLED, peopleKilled);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (characterItem.getKilled_by() != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("killed_by", new JSONArray(characterItem.getKilled_by()));
                String killedBy = jsonObject.toString();
                values.put(Params.KEY_KILLED_BY, killedBy);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (characterItem.getParents() != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("parents", new JSONArray(characterItem.getParents()));
                String parents = jsonObject.toString();
                values.put(Params.KEY_PARENTS, parents);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (characterItem.getSiblings() != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("siblings", new JSONArray(characterItem.getSiblings()));
                String siblings = jsonObject.toString();
                values.put(Params.KEY_SIBLINGS, siblings);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (characterItem.getSpouse() != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("spouse", new JSONArray(characterItem.getSpouse()));
                String spouse = jsonObject.toString();
                values.put(Params.KEY_SPOUSE, spouse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (characterItem.getChildren() != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("children", new JSONArray(characterItem.getChildren()));
                String children = jsonObject.toString();
                values.put(Params.KEY_CHILDREN, children);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (characterItem.getFavourite()) {
            values.put(Params.KEY_FAVOURITE, "1");
        } else {
            values.put(Params.KEY_FAVOURITE, "0");
        }
        db.insert(Params.TABLE_NAME, null, values);
        db.close();
    }

    public List getCharacterItemList() {
        List<CharacterItem> characterItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                List<String> peopleKilled = new ArrayList<>();
                if (cursor.getString(4) != null) {
                    try {
                        JSONObject jsonPeopleKilled = new JSONObject(cursor.getString(4));
                        JSONArray jsonArrayPeopleKilled = jsonPeopleKilled.getJSONArray("people_killed");
                        if (jsonArrayPeopleKilled != null) {
                            for (int i = 0; i < jsonArrayPeopleKilled.length(); i++) {
                                peopleKilled.add(jsonArrayPeopleKilled.optString(i));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                List<String> KilledBy = new ArrayList<>();
                if (cursor.getString(5) != null) {
                    try {
                        JSONObject jsonKilledBy = new JSONObject(cursor.getString(5));
                        JSONArray jsonArrayKilledBy = jsonKilledBy.getJSONArray("killed_by");
                        if (jsonArrayKilledBy != null) {
                            for (int i = 0; i < jsonArrayKilledBy.length(); i++) {
                                KilledBy.add(jsonArrayKilledBy.optString(i));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                List<String> parents = new ArrayList<>();
                if (cursor.getString(6) != null) {
                    try {
                        JSONObject jsonParents = new JSONObject(cursor.getString(6));
                        JSONArray jsonArrayParents = jsonParents.getJSONArray("parents");
                        if (jsonArrayParents != null) {
                            for (int i = 0; i < jsonArrayParents.length(); i++) {
                                parents.add(jsonArrayParents.optString(i));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                List<String> sibling = new ArrayList<>();
                if (cursor.getString(7) != null) {
                    try {
                        JSONObject jsonSibling = new JSONObject(cursor.getString(7));
                        JSONArray jsonArraySibling = jsonSibling.getJSONArray("siblings");
                        if (jsonArraySibling != null) {
                            for (int i = 0; i < jsonArraySibling.length(); i++) {
                                sibling.add(jsonArraySibling.optString(i));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                List<String> spouse = new ArrayList<>();
                if (cursor.getString(8) != null) {
                    try {
                        JSONObject jsonSpouse = new JSONObject(cursor.getString(8));
                        JSONArray jsonArraySpouse = jsonSpouse.getJSONArray("spouse");
                        if (jsonArraySpouse != null) {
                            for (int i = 0; i < jsonArraySpouse.length(); i++) {
                                spouse.add(jsonArraySpouse.optString(i));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                List<String> children = new ArrayList<>();
                if (cursor.getString(9) != null) {
                    try {
                        JSONObject jsonChildren = new JSONObject(cursor.getString(9));
                        JSONArray jsonArrayChildren = jsonChildren.getJSONArray("people_killed");
                        if (jsonArrayChildren != null) {
                            for (int i = 0; i < jsonArrayChildren.length(); i++) {
                                children.add(jsonArrayChildren.optString(i));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                CharacterItem characterItem = new CharacterItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), peopleKilled,
                        KilledBy, parents, sibling, spouse, children);

                characterItem.setFavourite(cursor.getString(10).equals("1"));

                characterItemList.add(characterItem);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return characterItemList;
    }

    public void updateFavouriteState(CharacterItem characterItem, int position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        if (characterItem.getFavourite()) {
            value.put(Params.KEY_FAVOURITE, "1");
        } else {
            value.put(Params.KEY_FAVOURITE, "0");
        }
        db.update(Params.TABLE_NAME, value, Params.KEY_ID + " = ?", new String[]{String.valueOf(position + 1)});
        db.close();
    }

    public int getCount() {
        String query = "SELECT  * FROM " + Params.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return count;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
