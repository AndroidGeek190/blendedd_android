package com.erginus.blendedd.CategoryModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by paramjeet on 10/7/15.
 */
public class CategoryModel implements  Serializable {

    String categryId, categryNm,categryStatus, categryType,categryOrder, categrySlug, title, city_name,
            state_code, image,price, city_id, post_desc, post_id, price_one, price_two, price_three, price_four, post_status;
    String firstname,lastname,loginid,login_password,confirm_login_password,email,primary_contact,dob;
    String country_name,country_slug,state_name,country_city_name;
    int country_id, state_id,country_city_id;
    String credit_card_name,credit_card_number,credit_card_cvv;
    String from,to,days,button_text,button_value,button_id,button_price,current_day,current_time;

    public String getbutton_text()
    {
        return button_text;
    }
    public void setbutton_text(String text)
    {
        this.button_text=text;
    }
    public String getbutton_value()
    {
        return button_value;
    }
    public void setbutton_value(String value)
    {
        this.button_value=value;
    }
    public void setButton_id(String btid)
    {
        this.button_id=btid;
    }
    public String getbutton_id()
    {
        return button_id;
    }
    public void setbutton_price(String btprice)
    {
        this.button_price=btprice;
    }
    public String getbutton_price()
    {
        return button_price;
    }
    public String getfrom()
    {
        return from;
    }
    public void setfrom(String from)
    {
        this.from=from;
    }
    public String getto()
    {
        return to;
    }
    public void setto(String to)
    {
        this.to=to;
    }
    public String getdays()
    {
        return days;
    }
    public void setcurrent_day(String currentday)
    {
        this.current_day=currentday;
    }
    public String getcurrent_day()
    {
        return current_day;
    }
    public void setcurrent_time(String currenttime)
    {
        this.current_time=currenttime;
    }
    public String getcurrent_time()
    {
        return current_time;
    }
    public void setdays(String days)
    {
        this.days=days;
    }
    public String getCredit_card_name()
    {
        return credit_card_name;
    }
    public void setCredit_card_name(String creditcardname)
    {
        this.credit_card_name=creditcardname;
    }
    public String getcredit_card_number()
    {
        return credit_card_number;
    }
    public void setcredit_card_number(String creditcardnumber)
    {
        this.credit_card_number=creditcardnumber;
    }
    public String getcredit_card_cvv()
    {
        return credit_card_cvv;
    }
    public void setCredit_card_cvv(String creditcardcvv)
    {
        this.credit_card_cvv=creditcardcvv;
    }
    public int getstate_id()
    {
        return state_id;
    }
    public void setstate_id(int stateid )
    {
        this.state_id=stateid;
    }
    public String getstate_name()
    {
        return state_name;
    }
    public void setstate_name(String statename )
    {
        this.state_name=statename;
    }
    public int getCountry_id(){
        return country_id;
    }
    public int getcountry_city_id()
    {
        return country_city_id;
    }
    public void setcountry_city_id(int cityid)
    {
        this.country_city_id=cityid;
    }
    public String getcountry_city_name()
    {
        return country_city_name;
    }
    public void setcountry_city_name(String cityname)
    {
        this.country_city_name=cityname;
    }

    public void setCountry_id(int countryid)
    {
        this.country_id=countryid;
    }
    public String getCountry_name()
    {
        return country_name;
    }
    public void setCountry_name(String countryname)
    {
        this.country_name=countryname;
    }
    public String getCountry_slug()
    {
        return country_slug;
    }
    public void setCountry_slug(String countryslug)
    {
        this.country_slug=countryslug;
    }
    public String getfirstname()
    {
        return firstname;
    }
    public void setfirstname(String fname)
    {
        this.firstname=fname;
    }
    public String getlastname()
    {
        return lastname;
    }
    public void setLastname(String lname)
    {
        this.lastname=lname;
    }
    public String getloginid()
    {
        return loginid;
    }
    public void setloginid(String id)
    {
        this.loginid=id;
    }

    public String getlogin_password()
    {
        return login_password;
    }

    public void setLogin_password(String loginpassword)
    {
        this.login_password=loginpassword;
    }
    public String getConfirm_login_password()
    {
        return confirm_login_password;
    }
    public void setConfirm_login_password(String confirmlogin)
    {
        this.confirm_login_password=confirmlogin;
    }
    public String getemail()
    {
        return email;
    }
    public void setemail(String email)
    {
        this.email=email;
    }
    public String getPrimary_contact()
    {
        return primary_contact;
    }
    public void setPrimary_contact(String contactno)
    {
        this.primary_contact=contactno;
    }

    public String getdob()
    {
        return dob;
    }
    public void setdob(String dateofbirth)
    {
        this.dob=dateofbirth;
    }
//******************************************Categories ***************************************************

    public  String getCategoryId()
    {
        return  categryId;
    }

    public void setCategoryId(String id)
    {
        this.categryId=id;
    }

    public  String getCategoryName()
    {
        return  categryNm;
    }

    public  void setCategoryName(String nm)
    {
        this.categryNm=nm;
    }

    public String getCategorySlug(){
        return categrySlug;
    }

    public  void setCategorySlug(String slug)
    {
        this.categrySlug=slug;
    }


    public String getCategoryType(){
        return categryType;
    }

    public  void setCategryType(String type)
    {
        this.categryType=type;
    }

    public String getCategoryStatus(){
        return categryStatus;
    }

    public  void setCategoryStatus(String status)
    {
        this.categryStatus=status;
    }

    public String getCategoryOrder(){
        return categryOrder;
    }

    public  void setCategoryOrder(String order)
    {
        this.categryOrder=order;
    }


    public String getCategoryTitle(){
        return title;
    }

    public  void setCategoryTitle(String title)
    {
        this.title=title;
    }
    public String getStateCode(){
        return state_code;
    }

    public  void setStateCode(String code)
    {
        this.state_code=code;
    }

    public String getImage(){
        return image;
    }

    public  void setImage(String image)
    {
        this.image=image;
    }

    public String getCityId(){
        return city_id;
    }

    public  void setCityId(String id)
    {
        this.city_id=id;

    }
    public String getCityName(){
        return city_name;
    }

    public  void setCityName(String city_name)
    {
        this.city_name=city_name;

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

    public String getPostDealPriceOne(){
        return price_one;
    }

    public  void setPostDealPriceOne(String price )
    {
        this.price_one=price;
    }

    public String getPostDealPricetwo(){
        return price_two;
    }

    public  void setPostDealPriceTwo(String price )
    {
        this.price_two=price;
    }

    public String getPostDealPriceThree(){
        return price_three;
    }

    public  void setPostDealPriceThree(String price )
    {
        this.price_three=price;
    }

    public String getPostDealPriceFour(){
        return price_four;
    }

    public  void setPostDealPriceFour(String price )
    {
        this.price_four=price;
    }


    public String getPostStatus(){
        return post_status;
    }

    public  void setPostStatus(String status)
    {
        this.post_status=status;
    }


}
