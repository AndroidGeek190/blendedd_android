package com.erginus.blendedd.CategoryModel;

import java.io.Serializable;

/**
 * Created by paramjeet on 10/7/15.
 */
public class ActivityModel implements  Serializable {

    String  image,price, post_desc, post_id;
    String from,to,days,button_text,button_value,button_id,button_price,current_day,current_time,date;

    public String getdate()
    {
        return date;
    }
    public void setdate(String text)
    {
        this.date=text;
    }


    public String getImage(){
        return image;
    }

    public  void setImage(String image)
    {
        this.image=image;
    }
    public String getDate(){
        return date;
    }

    public  void setDate(String date)
    {
        this.date=date;
    }


    public String getPrice(){
        return price;
    }

    public  void setPrice(String price)
    {
        this.price=price;
    }

    public String getPostDescription(){
        return post_desc;
    }

    public  void setPostDescription(String post_desc)
    {
        this.post_desc=post_desc;
    }

    public String getPostId(){
        return post_id;
    }

    public  void setPostId(String post_id)
    {
        this.post_id=post_id;
    }




}
