package fr.thebigtouffe.unescoworldheritage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.thebigtouffe.unescoworldheritage.UNESCO.Country;

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
            viewHolder.name_fr = (TextView) convertView.findViewById(R.id.country_row_name_fr);
            convertView.setTag(viewHolder);
        }

        Country country = getItem(position);

        viewHolder.name.setText(country.getName());

        String subtext = "Cultural : "+country.getNumberCulturalSites();
        subtext += " Natural : "+country.getNumberNaturalSites();
        subtext += " Mixed : "+country.getNumberMixedSites();
        viewHolder.name_fr.setText(subtext);

        return convertView;
    }

    private class CountryRowViewHolder {
        public TextView name;
        public TextView name_fr;
    }
}