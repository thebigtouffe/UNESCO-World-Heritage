package fr.thebigtouffe.heritage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.thebigtouffe.heritage.UNESCO.Country;

public class countryRowAdapter extends ArrayAdapter<Country>  {

    public countryRowAdapter(Context context, ArrayList<Country> countries) {
        super(context, 0, countries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.country_row,parent, false);
        }

        CountryRowViewHolder viewHolder = (CountryRowViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new CountryRowViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.country_row_name);
            viewHolder.category_count = (TextView) convertView.findViewById(R.id.country_row_category_count);
            viewHolder.flag = (ImageView) convertView.findViewById(R.id.country_flag);
            convertView.setTag(viewHolder);
        }

        Country country = getItem(position);

        viewHolder.name.setText(country.getName());

        viewHolder.flag.setImageResource(getContext().getResources().getIdentifier("flag_" +
                        country.getIso().toLowerCase(), "drawable", getContext().getPackageName()));

        String subtext = String.format(getContext().getResources().getString(R.string.category_count),
                ""+country.getNumberCulturalSites(),
                ""+country.getNumberNaturalSites(),
                ""+country.getNumberMixedSites());
        viewHolder.category_count.setText(subtext);

        return convertView;
    }

    private class CountryRowViewHolder {
        public TextView name;
        public TextView category_count;
        public ImageView flag;
    }
}