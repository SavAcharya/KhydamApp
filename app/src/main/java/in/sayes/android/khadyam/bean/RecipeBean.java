package in.sayes.android.khadyam.bean;

import java.util.ArrayList;

/**
 * @author sourav
 *
 */
public class RecipeBean {

private String recipeId;
private String recipeName;
private ArrayList<String> mImageUrls;
private String recipeDetails;
private ArrayList<TipsBean> tips;
private ArrayList<CatagoryBean> catagory;
private ArrayList<RecipeSteps> steps;
private ArrayList<Ingredients> ingredients;
private String mImageThumb;
private String cookingTime;
private String preprationTime;


public static class RecipeSteps{
	
	private String stepsTitle;
	private String stepsDetails;
	
	public String getStepsTitle() {
		return stepsTitle;
	}
	public void setStepsTitle(String stepsTitle) {
		this.stepsTitle = stepsTitle;
	}
	public String getStepsDetails() {
		return stepsDetails;
	}
	public void setStepsDetails(String stepsDetails) {
		this.stepsDetails = stepsDetails;
	}
	
}

public static class Ingredients{
	
private String ingredientName;
private String ingredientsQuantity;
private String ingredientsUnit;


public String getIngredientName() {
	return ingredientName;
}
public void setIngredientName(String ingredientName) {
	this.ingredientName = ingredientName;
}
public String getIngredientsQuantity() {
	return ingredientsQuantity;
}
public void setIngredientsQuantity(String ingredientsQuantity) {
	this.ingredientsQuantity = ingredientsQuantity;
}
public String getIngredientsUnit() {
	return ingredientsUnit;
}
public void setIngredientsUnit(String ingredientsUnit) {
	this.ingredientsUnit = ingredientsUnit;
}
}


public String getRecipeId() {
	return recipeId;
}

public void setRecipeId(String recipeId) {
	this.recipeId = recipeId;
}

public String getRecipeName() {
	return recipeName;
}

public void setRecipeName(String recipeName) {
	this.recipeName = recipeName;
}



public String getRecipeDetails() {
	return recipeDetails;
}

public void setRecipeDetails(String recipeDetails) {
	this.recipeDetails = recipeDetails;
}

public ArrayList<TipsBean> getTips() {
	return tips;
}

public void setTips(ArrayList<TipsBean> tips) {
	this.tips = tips;
}

public ArrayList<CatagoryBean> getCatagory() {
	return catagory;
}

public void setCatagory(ArrayList<CatagoryBean> catagory) {
	this.catagory = catagory;
}

public ArrayList<RecipeSteps> getSteps() {
	return steps;
}

public void setSteps(ArrayList<RecipeSteps> steps) {
	this.steps = steps;
}

public ArrayList<Ingredients> getIngredients() {
	return ingredients;
}

public void setIngredients(ArrayList<Ingredients> ingredients) {
	this.ingredients = ingredients;
}

public ArrayList<String> getmImageUrls() {
	return mImageUrls;
}

public void setmImageUrls(ArrayList<String> mImageUrls) {
	this.mImageUrls = mImageUrls;
}

public String getmImageThumb() {
	return mImageThumb;
}

public void setmImageThumb(String mImageThumb) {
	this.mImageThumb = mImageThumb;
}

public String getCookingTime() {
	return cookingTime;
}

public void setCookingTime(String cookingTime) {
	this.cookingTime = cookingTime;
}

public String getPreprationTime() {
	return preprationTime;
}

public void setPreprationTime(String preprationTime) {
	this.preprationTime = preprationTime;
}



}
