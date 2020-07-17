package server.server_resources.manager_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.AccountsException;
import org.restlet.resource.*;
import server.controller.accounts.ManagerAccountController;
import server.model.Category;
import server.model.enumerations.PropertyType;

import java.util.HashMap;

public class CategoryResource extends ServerResource {
    @Get
    public String getCategory(){
        Category category = Category.getCategoryByName(getQueryValue("name"));
        return new YaGson().toJson(category, String.class);
    }

    @Post
    public void createCategory(String propertiesJson)  {
        HashMap<String, PropertyType> properties = new YaGson().fromJson(propertiesJson, new TypeToken<HashMap<String, PropertyType>>(){}.getType());
        try {
            ManagerAccountController.getInstance().createCategory(getQueryValue("name"), getQueryValue("parentName"), properties);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    @Put
    public void editCategory(String toEditJson)  {
        HashMap<String, String > toEdit = new YaGson().fromJson(toEditJson, new TypeToken<HashMap<String, String>>(){}.getType());
        try {
            ManagerAccountController.getInstance().editCategory(getQueryValue("name"), toEdit);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    @Delete
    public void removeCategory(){
        try {
            ManagerAccountController.getInstance().removeCategory(getQueryValue("categoryName"));
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }

    }
}
