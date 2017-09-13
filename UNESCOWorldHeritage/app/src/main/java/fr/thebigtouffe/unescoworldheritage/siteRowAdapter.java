package fr.thebigtouffe.unescoworldheritage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.thebigtouffe.unescoworldheritage.UNESCO.Site;

public class siteRowAdapter extends ArrayAdapter<Site>  {

    public siteRowAdapter(Context context, ArrayList<Site> sites) {
        super(context, 0, sites);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.site_row,parent, false);
        }

        SiteRowViewHolder viewHolder = (SiteRowViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SiteRowViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.site_row_name);
            viewHolder.subtext = (TextView) convertView.findViewById(R.id.site_row_subtext);
            convertView.setTag(viewHolder);
        }

        Site site = getItem(position);

        viewHolder.name.setText(site.getName());
        viewHolder.subtext.setText(site.getCategory().getName());

        return convertView;
    }

    private class SiteRowViewHolder {
        public TextView name;
        public TextView subtext;
    }
}