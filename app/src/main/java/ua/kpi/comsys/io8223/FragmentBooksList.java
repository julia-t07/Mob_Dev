package ua.kpi.comsys.io8223;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ua.kpi.comsys.io8223.model.Book;
import ua.kpi.comsys.io8223.model.BooksList;


public class FragmentBooksList extends Fragment {

    BooksList booksList;
    ListView booksListView;

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> subtitles = new ArrayList<>();
    ArrayList<String> isbn13s = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        booksListView = (ListView) view.findViewById(R.id.booksListView);
        AdapterBooksList adapterBooksList = new AdapterBooksList(this.getContext(), titles,
                subtitles, isbn13s, prices, images);
        booksListView.setAdapter(adapterBooksList);

        if (adapterBooksList.getCount() == 0) {
            parseFromFile("books_list");
            assignFields(booksList);
        }

//        System.out.println(booksList.getBooks().get(1).getTitle());
//        System.out.println(booksListView.get);
    }


    //Opens file, reads its contents
    private void parseFromFile(String fileName) {
        Gson gson = new Gson();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(getFileLocation(fileName)));
            booksList = gson.fromJson(br, BooksList.class);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void assignFields(BooksList booksList) {
        for (Book book : booksList.getBooks()) {
            try {
                if (!book.getTitle().trim().equals(""))
                    titles.add(book.getTitle());
                else
                    titles.add("");
                if (!book.getSubtitle().trim().equals(""))
                    subtitles.add(book.getSubtitle());
                else
                    subtitles.add("");
                if (!book.getIsbn13().trim().equals(""))
                    isbn13s.add(book.getIsbn13());
                else
                    isbn13s.add("");
                if (!book.getPrice().trim().equals(""))
                    prices.add(book.getPrice());
                else
                    prices.add("");
                if (!book.getImage().trim().equals(""))
                    images.add(book.getImage());
                else
                    images.add("");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private InputStream getFileLocation(String fileName) {
        return getResources().openRawResource(getResources().getIdentifier(fileName,
                "raw", this.getContext().getPackageName()));
    }

}
