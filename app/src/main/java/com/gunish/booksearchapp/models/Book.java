package com.gunish.booksearchapp.models;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Book {
    private String openLibraryId;
    private String author;
    private String title;

    public String getOpenLibraryId() {
        return openLibraryId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    // Get medium sized book cover from covers API
    public String getCoverUrl()
    {
        return "http://covers.openlibrary.org/b/olid/" + openLibraryId + "-M.jpg?default=false";
    }

    // Get large sized book cover from covers API
    public String getLargeCoverUrl() {
        return "http://covers.openlibrary.org/b/olid/" + openLibraryId + "-L.jpg?default=false";
    }


    public static Book fromJSON(JSONObject jsonObject)
    {
        Book book=new Book();
        try {
            if(jsonObject.has("cover_edition_key"))
            {
                book.openLibraryId= jsonObject.getString("cover_edition_key");
            }
            else if(jsonObject.has("edition_key"))
            {
                final JSONArray ids= jsonObject.getJSONArray("edition_key");
                book.openLibraryId=ids.getString(0);
            }
            book.title=jsonObject.has("title_suggest")?jsonObject.getString("title_suggest"):"";
            book.author = getAuthor(jsonObject);
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return book;
    }

    private static String getAuthor(JSONObject jsonObject) {
        try {
            final JSONArray authors = jsonObject.getJSONArray("author_name");
            int numAuthors = authors.length();
            final String[] authorStrings = new String[numAuthors];
            for (int i = 0; i < numAuthors; ++i) {
                authorStrings[i] = authors.getString(i);
            }
            return TextUtils.join(", ", authorStrings);
        } catch (JSONException e) {
            return "";
        }


    }

}
