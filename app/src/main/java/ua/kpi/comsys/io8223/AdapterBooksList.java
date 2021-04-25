package ua.kpi.comsys.io8223;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterBooksList extends ArrayAdapter<String> {

    ArrayList<String> titles;
    ArrayList<String> subtitles;
    ArrayList<String> isbn13s;
    ArrayList<String> prices;
    ArrayList<String> images;

    Context mContext;

    public AdapterBooksList(Context context, ArrayList<String> titles, ArrayList<String> subtitles,
                             ArrayList<String> isbn13s, ArrayList<String> prices,
                             ArrayList<String> images) {

        super(context, R.layout.listview_item);
        this.titles = titles;
        this.subtitles = subtitles;
        this.isbn13s = isbn13s;
        this.prices = prices;
        this.images = images;

        this.mContext = context;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ViewHolder mViewHolder = new ViewHolder();
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) mContext.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.listview_item, parent, false);


                mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.title);
                mViewHolder.mSubtitle = (TextView) convertView.findViewById(R.id.subtitle);
                mViewHolder.mIsbn13 = (TextView) convertView.findViewById(R.id.isbn13);
                mViewHolder.mPrice = (TextView) convertView.findViewById(R.id.price);
                mViewHolder.mImage = (ImageView) convertView.findViewById(R.id.image);

                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            mViewHolder.mTitle.setText(titles.get(position));
            mViewHolder.mSubtitle.setText(subtitles.get(position));
            mViewHolder.mIsbn13.setText(isbn13s.get(position));
            mViewHolder.mPrice.setText(prices.get(position));

            mViewHolder.mImage.setImageResource(mContext.getResources().getIdentifier(
                    images.get(position), "drawable", mContext.getPackageName()));

        } catch (Resources.NotFoundException | IndexOutOfBoundsException |
                NumberFormatException e) {

            e.printStackTrace();
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView mTitle;
        TextView mSubtitle;
        TextView mIsbn13;
        TextView mPrice;
        ImageView mImage;
    }
}
