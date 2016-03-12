package com.gdgvitvellore.developers.gdgvitvellore.AboutUs;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

/**
 * Created by Gautam on 3/22/2015.
 */
public class AboutUsActivity extends ActionBarActivity
{
//start learning and using fragments in the place of activities{
     private BarChart chart;//only im gonna use it,i mean ony this graph
    private PieChart pieChart,p2,p3,p4;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);//the layout and the view inflation is done here by android rather than you doing it automatically

        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal)));
        actionBar.setTitle("ABOUT US");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //chart = (BarChart) findViewById(R.id.bargraph);
        //chart.setBackgroundColor(getResources().getColor(R.color.dddgray));

        pieChart =(PieChart) findViewById(R.id.piegraph);
        pieChart.setHoleRadius(75f);//third change
        pieChart.setHoleColor(getResources().getColor(R.color.dddgray));
        pieChart.setDescription("");
        pieChart.setDrawSliceText(false);
        pieChart.setDrawMarkerViews(false);
        pieChart.setDrawCenterText(false);
        pieChart.setCenterText("");



        p2 =(PieChart) findViewById(R.id.piegraph2);
        p2.setHoleRadius(75f);//third change
        p2.setHoleColor(getResources().getColor(R.color.dddgray));
        p2.setDescriptionTextSize(0f);

        p3 =(PieChart) findViewById(R.id.piegraph3);
        p3.setHoleRadius(75f);//third change
        p3.setHoleColor(getResources().getColor(R.color.dddgray));
        p3.setDescriptionTextSize(0f);

        p4 =(PieChart) findViewById(R.id.piegraph4);
        p4.setHoleRadius(75f);//third change
        p4.setHoleColor(getResources().getColor(R.color.dddgray));//no changes to be made here
        p4.setDescriptionTextSize(0f);

        setData();




    }

    public void setData()
    {

       /* ArrayList< BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));*/
        /*entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));*/

        //----------pie-Entry----------------

        ArrayList<Entry> entries2 = new ArrayList<>();//charting entry
        entries2.add(new Entry(80, 0));
        entries2.add(new Entry(20, 1));

        ArrayList<Entry> entries3 = new ArrayList<>();//charting entry
        entries3.add(new Entry(65, 0));
        entries3.add(new Entry(35, 1));

        ArrayList<Entry> entries4 = new ArrayList<>();//charting entry
        entries4.add(new Entry(70, 0));
        entries4.add(new Entry(30, 1));

        ArrayList<Entry> entries5 = new ArrayList<>();//charting entry
        entries5.add(new Entry(95, 0));
        entries5.add(new Entry(5, 1));//done


        //----------------------------------


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("");
        labels.add("");//for the black part
        labels.add("March");
        labels.add("April");
        /*labels.add("May");
        labels.add("June");*/

        //BarDataSet dataset = new BarDataSet(entries, "# of Calls");
        //dataset.setColors(Bartemplate.BAR_WHITE);//make this all white-->v.v.v. easy


        //BarData data = new BarData(labels, dataset);
        //chart.setData(data);

        //chart.setDescription("GDG Work Flow Stats...");
        //chart.animateY(5000);

        //------------piegraph------------------------
        PieDataSet dataset2 = new PieDataSet(entries2, "");//make this an empty string instead
        dataset2.setColors(Colortemplate.GOUTHAM_COLORS);//add a separate textview in the framelayout for the titles of each piechart

        PieData data2 = new PieData(labels, dataset2);
        pieChart.setData(data2);



       // pieChart.setDescription("# of times Alice called Bob");
        pieChart.animateY(5000);
        //---


        PieDataSet dataset3 = new PieDataSet(entries3, "");//make this an empty string instead
        dataset3.setColors(Colortemplate2.GOUTHAM_COLORS2);

        PieData data3 = new PieData(labels, dataset3);
        p2.setData(data3);

       // p2.setDescription("# of times Alice called Bob");
        p2.animateY(5000);

        //----


        PieDataSet dataset4 = new PieDataSet(entries4, "");//make this an empty string instead
        dataset4.setColors(Colortemplate3.GOUTHAM_COLORS3);

        PieData data4 = new PieData(labels, dataset4);
        p3.setData(data4);

       // p3.setDescription("# of times Alice called Bob");
        p3.animateY(5000);

        //------


        PieDataSet dataset5 = new PieDataSet(entries5, "");//make this an empty string instead
        dataset5.setColors(Colortemplate4.GOUTHAM_COLORS4);

        PieData data5 = new PieData(labels, dataset5);
        p4.setData(data5);

        //p4.setDescription("# of times Alice called Bob");
        p4.animateY(5000);



        //---------------------------------------------

        LimitLine line = new LimitLine(10f);
       // data.addLimitLine(line);


    }


}



//remove the text on the pie graph in the end after adding the %textview
