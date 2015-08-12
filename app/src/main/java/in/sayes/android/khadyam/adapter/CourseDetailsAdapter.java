package in.sayes.android.khadyam.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.activity.RecipeDetailsActivity;
import in.sayes.android.khadyam.bean.CoursesBean;
import in.sayes.android.khadyam.bean.RecipeBean;
import in.sayes.android.khadyam.common.AppConstants;


public class CourseDetailsAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<CoursesBean.CourseDetails> days;
    private boolean isMyCourse;
    private ImageLoader imageLoader= ImageLoader.getInstance();
    private DisplayImageOptions options;

    public CourseDetailsAdapter(Context context, ArrayList<CoursesBean.CourseDetails> days,boolean courseType) {
        this.context = context;
        this.days = days;
        this.isMyCourse=courseType;
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.chef_logo).showImageForEmptyUri(R.drawable.chef_logo).cacheInMemory().cacheOnDisc().build();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<RecipeBean> recipesByDays = days.get(groupPosition).getItems();
        return recipesByDays.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Nullable
    @Override
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, @Nullable View convertView, ViewGroup parent) {

        @NotNull RecipeBean recipes = (RecipeBean) getChild(groupPosition, childPosition);
        if (convertView == null) {
            @NotNull LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_row_recipe, null);
        }
        @NotNull TextView txtRecipeName = (TextView) convertView.findViewById(R.id.txt_recipeName);
        @NotNull ImageView imgRecipeImage = (ImageView) convertView.findViewById(R.id.img_recipeImg);
        @NotNull TextView txtRecipeDescription=(TextView) convertView.findViewById(R.id.txt_recipeDesc);
        txtRecipeName.setText(recipes.getRecipeName().toString());
        txtRecipeDescription.setText(recipes.getRecipeDetails());

        imageLoader.displayImage(recipes.getmImageThumb(),imgRecipeImage,options);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<RecipeBean> chList = days.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return days.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return days.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Nullable
    @Override
    public View getGroupView(int courseDaysPosition, boolean isExpanded,
            @Nullable View convertView, ViewGroup parent) {
        @NotNull CoursesBean.CourseDetails days = (CoursesBean.CourseDetails) getGroup(courseDaysPosition);
        if (convertView == null) {
            @NotNull LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.group_item, null);
        }
        @NotNull TextView tv = (TextView) convertView.findViewById(R.id.txt_courseDay);
        tv.setText(days.getTitle());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        if(isMyCourse){

            @NotNull RecipeBean recipes = (RecipeBean) getChild(groupPosition, childPosition);
            @NotNull Intent i=new Intent(context, RecipeDetailsActivity.class);

            i.putExtra(AppConstants.RECIPE_ID, recipes.getRecipeId());
            context.startActivity(i);
        }


        return true;
    }


}
