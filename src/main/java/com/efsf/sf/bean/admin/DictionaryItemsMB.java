package com.efsf.sf.bean.admin;

import com.efsf.sf.collection.DictionaryItem;
import com.efsf.sf.sql.dao.GenericDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class DictionaryItemsMB implements Serializable {
    
    private List<DictionaryItem> items;
    private int type = 0;
    private DictionaryItem item = new DictionaryItem(1, "test", 1);

    public String showDictionary(int type) {
        this.type = type;
        return "/admin/dictionary?faces-redirect=true";
    }

    public void addItem() {
        item = new DictionaryItem(-1, "", 1);
    }

    public void saveItem() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        Class<?> cls = Class.forName("com.efsf.sf.sql.entity." + getClassByType());
        GenericDao<?> dao = new GenericDao(cls);
        Object o = cls.newInstance();

        Field name = cls.getDeclaredField("name");
        name.setAccessible(true);
        name.set(o, item.getName());

        Field active = cls.getDeclaredField("isActive");
        active.setAccessible(true);
        active.set(o, item.getIsActive());

        if (item.getId() != -1) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().startsWith("id")) {
                    field.setAccessible(true);
                    field.set(o, item.getId());
                }
            }
        }

        dao.saveOrUpdateObject(o);
        item = new DictionaryItem(-1, "", 1);
    }

    public List<DictionaryItem> getItems() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        String name = null;
        Integer id = null;
        Integer isActive = 1;

        items = new ArrayList<DictionaryItem>();

        Class<?> cls = Class.forName("com.efsf.sf.sql.entity." + getClassByType());
        GenericDao<?> dao = new GenericDao(cls);
        List<?> ls = dao.getAll();

        Field[] fields = ls.get(0).getClass().getDeclaredFields();
        for (int i = 0; i < ls.size(); i++) {
            for (Field field : fields) {
                field.setAccessible(true);

                if (field.getName().equals("name")) {
                    name = (String) field.get(ls.get(i));
                } else if (field.getName().startsWith("id")) {
                    id = (Integer) field.get(ls.get(i));
                } else if (field.getName().equals("isActive")) {
                    isActive = (Integer) field.get(ls.get(i));
                }

            }
            items.add(new DictionaryItem(id, name, isActive));
        }

        return items;
    }

    public void setItems(List<DictionaryItem> items) {
        this.items = items;
    }

    private String getClassByType() {
        switch (type) {
            case 0:
                return "ProductType";
            case 1:
                return "Branch";
            case 2:
                return "CaseStatus";
            case 3:
                return "Education";
            case 4:
                return "MaritalStatus";
            case 5:
                return "WorkingPlace";
        }

        return null;
    }

    public DictionaryItem getItem() {
        return item;
    }

    public void setItem(DictionaryItem item) {
        this.item = item;
    }

   
}
