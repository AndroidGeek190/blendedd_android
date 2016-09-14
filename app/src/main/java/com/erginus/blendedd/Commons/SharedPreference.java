package com.erginus.blendedd.Commons;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Created by nazer on 11/4/2015.
 */
public class SharedPreference {
    private Context context;
    public static SharedPreferences preferences;

    public SharedPreference(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Context getContext() {
        return context;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setfacebookname(String name) {
        Editor edit = getPreferences().edit();
        edit.putString("facebook_name", name);
        edit.commit();
    }

    public String getfacebookname() {
        return getPreferences().getString("facebook_name", "null");
    }

    public void setfirstname(String name) {
        Editor edit = getPreferences().edit();
        edit.putString("user_first_name", name);
        edit.commit();

    }

    public String getfirstname() {
        return getPreferences().getString("user_first_name", "null");
    }

    public void setlastname(String name) {
        Editor edit = getPreferences().edit();
        edit.putString("user_last_name", name);
        edit.commit();

    }

    public String getlastname() {
        return getPreferences().getString("user_last_name", "null");
    }

    public void setemail(String email) {
        Editor edit = getPreferences().edit();
        edit.putString("user_email", email);
        edit.commit();

    }

    public String getemail() {
        return getPreferences().getString("user_email", "null");
    }

    public void setcontactnumber(String contact) {
        Editor edit = getPreferences().edit();
        edit.putString("user_primary_contact", contact);
        edit.commit();

    }

    public String getcontactnumber() {
        return getPreferences().getString("user_primary_contact", "null");
    }

    public String getuserstatus() {

        return getPreferences().getString("user_status", "0");
    }

    public void setuserstatus(String status) {
        Editor edit = getPreferences().edit();
        edit.putString("user_status", status);
        edit.commit();

    }

    public void setuserloginname(String userid) {
        Editor edit = getPreferences().edit();
        edit.putString("user_login", userid);
        edit.commit();

    }

    public String getuserloginname() {

        return getPreferences().getString("user_login", "null");
    }

    public String getfacebookid() {

        return getPreferences().getString("facebook_id", "null");
    }

    public void setfacebookid(String fbid) {
        Editor edit = getPreferences().edit();
        edit.putString("facebook_id", fbid);
        edit.commit();

    }

    public void setbday(String bday) {
        Editor edit = getPreferences().edit();
        edit.putString("user_dob", bday);
        edit.commit();

    }

    public String getbday() {

        return getPreferences().getString("user_dob", "null");
    }

    public void setpassword(String pass) {
        Editor edit = getPreferences().edit();
        edit.putString("user_login_password", pass);
        edit.commit();

    }

    public String getpassword() {
        return getPreferences().getString("user_login_password", "null");
    }

    public void setconfirmpassword(String pass) {
        Editor edit = getPreferences().edit();
        edit.putString("confirm_user_login_password", pass);
        edit.commit();

    }

    public String getconfirmpassword() {
        return getPreferences().getString("confirm_user_login_password", "null");
    }


    public void setuserid(String userid) {
        Editor edit = getPreferences().edit();
        edit.putString("user_id", userid);
        edit.commit();

    }

    public String getuserid() {

        return getPreferences().getString("user_id", "null");
    }

    public void sethashcode(String sec) {
        Editor edit = getPreferences().edit();
        edit.putString("user_security_hash", sec);
        edit.commit();

    }

    public String gethashcode() {
        return getPreferences().getString("user_security_hash", "null");
    }


    public void setusertype(String user) {
        Editor edit = getPreferences().edit();
        edit.putString("user_type", user);
        edit.commit();
    }

    public String getusertype() {
        return getPreferences().getString("user_type", "null");
    }




    public void setprofileimage(String image) {
        Editor edit = getPreferences().edit();
        edit.putString("user_profile_image_url", image);
        edit.commit();

    }

    public String getprofileimage() {
        return getPreferences().getString("user_profile_image_url", "");
    }

    //==============================Signup two===================================

    public void setaddressone(String addressone) {
        Editor edit = getPreferences().edit();
        edit.putString("user_address_line_1", addressone);
        edit.commit();
    }

    public String getaddressone() {
        return getPreferences().getString("user_address_line_1", "Address Line 1");
    }

    public void setaddresstwo(String addresstwo) {
        Editor edit = getPreferences().edit();
        edit.putString("user_address_line_2", addresstwo);
        edit.commit();
    }

    public String getaddresstwo() {
        return getPreferences().getString("user_address_line_2", "Address Line 2");
    }

    public void setzipcode(String zipcode) {
        Editor edit = getPreferences().edit();
        edit.putString("user_zipcode", zipcode);
        edit.commit();
    }

    public String getzipcode() {
        return getPreferences().getString("user_zipcode", "Zipcode");
    }

    public void setcountryid(int countryId) {
        Editor edit = getPreferences().edit();
        edit.putInt("countries_id", countryId);
        edit.commit();

    }
    public int getcountryid() {
        return getPreferences().getInt("countries_id", 0);
    }

    public void setstateid(int stateId) {
        Editor edit = getPreferences().edit();
        edit.putInt("state_id",stateId);
        edit.commit();
    }

    public int getstateid() {

        return getPreferences().getInt("state_id", 0);
    }

    public void setcityid(int countryId) {
        Editor edit = getPreferences().edit();
        edit.putInt("city_id",countryId);
        edit.commit();
    }

    public int getcityid() {
        return getPreferences().getInt("city_id", 0);
    }

//    public void setloginwith(String login) {
//        Editor edit = getPreferences().edit();
//        edit.putString("login_with", login);
//        edit.commit();
//    }
//    public String getloginwith() {
//        return getPreferences().getString("login_with", "");
//    }

//===================================================sign up three=======================

    public void setBankname(String cardname) {
        Editor edit = getPreferences().edit();
        edit.putString("Bank_Name", cardname);
        edit.commit();
    }

    public String getBankname() {
        return getPreferences().getString("Bank_Name", "null");
    }

    public void setBank_Account_number(String cardname) {
        Editor edit = getPreferences().edit();
        edit.putString("Account_Number", cardname);
        edit.commit();
    }

    public String getBank_Account_number() {
        return getPreferences().getString("Account_Number", "null");
    }
    public void setBank_route_number(String cardname) {
        Editor edit = getPreferences().edit();
        edit.putString("Bank_route", cardname);
        edit.commit();
    }

    public String getBank_route_number() {
        return getPreferences().getString("Bank_route", "null");
    }


    public void setcardname(String cardname) {
    Editor edit = getPreferences().edit();
    edit.putString("user_credit_card_name", cardname);
    edit.commit();
}

    public String getcardname() {
        return getPreferences().getString("user_credit_card_name", "null");
    }
    public void setcreditcardnumber(String creditcard) {
        Editor edit = getPreferences().edit();
        edit.putString("user_credit_card_number", creditcard);
        edit.commit();
    }

    public String getcreditcardnumber() {
        return getPreferences().getString("user_credit_card_number", "");
    }




    public void setcardcvv(String cardcvv) {
        Editor edit = getPreferences().edit();
        edit.putString("user_credit_card_cvv", cardcvv);
        edit.commit();
    }

    public String getcardcvv() {
        return getPreferences().getString("user_credit_card_cvv", "null");
    }

    public int getcardexpirymonth() {
        return getPreferences().getInt("user_credit_card_expiry_month", 0);
    }

    public void setcardexpirymonth(int cardmonth) {
        Editor edit = getPreferences().edit();
        edit.putInt("user_credit_card_expiry_month", cardmonth);
        edit.commit();
    }

    public void setcardexpiryyear(int cardyear) {
        Editor edit = getPreferences().edit();
        edit.putInt("user_credit_card_expiry_year", cardyear);
        edit.commit();
    }

    public int getcardexpiryyear() {
        return getPreferences().getInt("user_credit_card_expiry_year", 0);
    }
//===================================Post id==============================
public void setproductid(String postid) {
    Editor edit = getPreferences().edit();
    edit.putString("product_id", postid);
    edit.commit();
}

    public String getproductid() {
        return getPreferences().getString("product_id", "");
    }

    public void setdrawer(String postid) {
        Editor edit = getPreferences().edit();
        edit.putString("no", postid);
        edit.commit();
    }

    public String getdrawer() {
        return getPreferences().getString("no", "");
    }



    public void setloginwith(String loginwith) {
        Editor edit = getPreferences().edit();
        edit.putString("login_with", loginwith);
        edit.commit();
    }

    public String getloginwith() {
        return getPreferences().getString("login_with", "null");
    }

    public void cleardata() {
        SharedPreferences.Editor editor = preferences.edit();
        // To delete all.
        editor.clear();
        // Delete based on key
        //editor.remove("username");
        editor.commit();
    }

}


